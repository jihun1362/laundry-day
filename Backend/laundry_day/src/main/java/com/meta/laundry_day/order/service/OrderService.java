package com.meta.laundry_day.order.service;

import com.meta.laundry_day.address_details.entity.AddressDetails;
import com.meta.laundry_day.address_details.repository.AddressDetailRepository;
import com.meta.laundry_day.alarm.entity.Alarm;
import com.meta.laundry_day.alarm.entity.AlarmType;
import com.meta.laundry_day.alarm.mapper.AlarmMapper;
import com.meta.laundry_day.alarm.repository.AlarmRepository;
import com.meta.laundry_day.common.config.S3Uploader;
import com.meta.laundry_day.common.exception.CustomException;
import com.meta.laundry_day.event_details.entity.EventDetails;
import com.meta.laundry_day.event_details.repository.EventDetailsRepository;
import com.meta.laundry_day.order.dto.LaundryRequestDto;
import com.meta.laundry_day.order.dto.LaundryResponseDto;
import com.meta.laundry_day.order.dto.OrderReaponseDto;
import com.meta.laundry_day.order.dto.OrderRequestDto;
import com.meta.laundry_day.order.dto.ProgressResponeDto;
import com.meta.laundry_day.order.entity.Laundry;
import com.meta.laundry_day.order.entity.LaundryStatus;
import com.meta.laundry_day.order.entity.Order;
import com.meta.laundry_day.order.entity.Progress;
import com.meta.laundry_day.order.entity.ProgressStatus;
import com.meta.laundry_day.order.mapper.OrderMapper;
import com.meta.laundry_day.order.repository.LaundryRepository;
import com.meta.laundry_day.order.repository.OrderRepository;
import com.meta.laundry_day.order.repository.ProgressRepository;
import com.meta.laundry_day.payment.entity.Card;
import com.meta.laundry_day.payment.entity.Payment;
import com.meta.laundry_day.payment.mapper.PaymentMapper;
import com.meta.laundry_day.payment.repository.CardRepository;
import com.meta.laundry_day.payment.repository.PaymentRepository;
import com.meta.laundry_day.payment.service.PaymentService;
import com.meta.laundry_day.stable_pricing.entity.StablePricing;
import com.meta.laundry_day.stable_pricing.repository.StablePricingRepository;
import com.meta.laundry_day.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.meta.laundry_day.common.message.ErrorCode.ADDRESS_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.AUTHORIZATION_DELETE_FAIL;
import static com.meta.laundry_day.common.message.ErrorCode.AUTHORIZATION_FAIL;
import static com.meta.laundry_day.common.message.ErrorCode.CARD_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.LAUNDRY_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.LAUNDRY_PICKUP_NOT_DONE_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.LAUNDRY_PICKUP_START_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.LAUNDRY_REGIST_DONE_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.LAUNDRY_RESIST_DONE_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.ORDER_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.ORDER_ONLY_ONE_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.PROGRESS_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.STABLEPRICING_NOT_FOUND;
import static com.meta.laundry_day.order.entity.ProgressStatus.수거진행중;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final AddressDetailRepository addressDetailRepository;
    private final CardRepository cardRepository;
    private final OrderRepository orderRepository;
    private final ProgressRepository progressRepository;
    private final LaundryRepository laundryRepository;
    private final StablePricingRepository stablePricingRepository;
    private final EventDetailsRepository eventDetailsRepository;
    private final PaymentRepository paymentRepository;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;
    private final S3Uploader s3Uploader;
    private final PaymentService paymentService;
    private final AlarmMapper alarmMapper;
    private final AlarmRepository alarmRepository;

    @Transactional
    public void createOrder(User user, OrderRequestDto requestDto, Long cardId) {
        List<Order> orders = orderRepository.findAllByUser(user);
        for (Order o : orders) {
            if (o.getStatus() == 1) throw new CustomException(ORDER_ONLY_ONE_ERROR);
        }

        AddressDetails address = addressDetailRepository.findByUser(user);
        if (address == null) throw new CustomException(ADDRESS_NOT_FOUND);

        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CustomException(CARD_NOT_FOUND));

        Order order = orderMapper.toOrder(requestDto, user, address, card);
        orderRepository.save(order);

        Progress progress = orderMapper.toProgress(order, user);
        progressRepository.save(progress);
        order.setProgress(progress);

        createAlarm(user, AlarmType.OrderComplete);
    }

    @Transactional(readOnly = true)
    public OrderReaponseDto orderDetail(User user, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));

        //권한체크
        if (!order.getUser().getId().equals(user.getId())) {
            throw new CustomException(AUTHORIZATION_FAIL);
        }

        return orderMapper.toResponse(order);
    }

    @Transactional
    public void createLaundry(User user, LaundryRequestDto requestDto, MultipartFile image, Long stablepriceId, Long orderId) throws IOException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));
        Progress progress = progressRepository.findByOrder(order);

        //세탁물 수거전 등록 불가
        if (!progress.getStatus().equals(ProgressStatus.세탁준비중)) {
            throw new CustomException(LAUNDRY_PICKUP_NOT_DONE_ERROR);
        }

        //세탁물 등록 완료전만 등록가능
        if (progress.getLaundryRegist() == 0) {
            throw new CustomException(LAUNDRY_RESIST_DONE_ERROR);
        }

        StablePricing stablePricing = stablePricingRepository.findById(stablepriceId).orElseThrow(() -> new CustomException(STABLEPRICING_NOT_FOUND));

        String imageUri = null;
        if (!image.isEmpty()) {
            imageUri = s3Uploader.upload(image, "laundry-images");
        }

        Laundry laundry = orderMapper.toLaundry(user, requestDto, imageUri, stablePricing, progress);

        laundryRepository.save(laundry);
    }

    @Transactional
    public void doneLaundry(User user, Long progressId) {
        Progress progress = progressRepository.findById(progressId).orElseThrow(() -> new CustomException(PROGRESS_NOT_FOUND));

        //세탁물 수거전 완료 불가
        if (!progress.getStatus().equals(ProgressStatus.세탁준비중)) {
            throw new CustomException(LAUNDRY_PICKUP_NOT_DONE_ERROR);
        }

        //페이먼트 중복으로 안생기게 제한
        if (progress.getLaundryRegist() == 0) {
            throw new CustomException(LAUNDRY_REGIST_DONE_ERROR);
        }

        progress.doneRegist();
        progress.update(ProgressStatus.세탁진행중);
        createAlarm(user, AlarmType.WashingStart);

        List<Laundry> laundrys = laundryRepository.findAllByProgress(progress);

        Long totalStablePrice = 0L;

        Long totalSurcharge = 0L;

        for (Laundry l : laundrys) {
            totalStablePrice += l.getStablePrice();
            totalSurcharge += l.getSurcharge();
        }

        Long amount = totalStablePrice + totalSurcharge;

        Long deliveryFee = paymentService.deliveryFeeCheck(amount);

        EventDetails event = eventDetailsRepository.findAllTopByOrderByDiscountRateDesc();

        Double discountRate = 0.0;

        if (event != null) {
            discountRate = event.getDiscountRate();
        }

        Long pay = amount + deliveryFee;
        Double discount = pay * discountRate * 0.01;
        double usePoint = 0;

        if (user.getPoint() - (pay - discount) >= 0) {
            usePoint = pay - discount;
        }

        Double totalAmount = pay - discount - usePoint;

        Payment payment = paymentMapper.toPayment(progress.getOrder().getId(), amount, usePoint, discountRate, totalStablePrice, totalSurcharge, deliveryFee, totalAmount);

        paymentRepository.save(payment);
    }

    @Transactional
    public void updateLaundry(String status, Long laundryId) {
        Laundry laundry = laundryRepository.findById(laundryId).orElseThrow(() -> new CustomException(LAUNDRY_NOT_FOUND));
        laundry.update(LaundryStatus.valueOf(status));
    }

    @Transactional(readOnly = true)
    public ProgressResponeDto progressCheck(User user) {
        List<Order> orders = orderRepository.findAllByUser(user);
        Order order = null;
        for (Order o : orders) {
            if (o.getStatus() == 1) order = o;
        }

        Progress progress = progressRepository.findByOrder(order);

        List<LaundryResponseDto> laundryResponseDtoList = new ArrayList<>();
        //세탁물 등록이 완료되야 검색
        if (progress.getLaundryRegist() == 0) {
            List<Laundry> laundrys = laundryRepository.findAllByProgress(progress);
            for (Laundry l : laundrys) {
                laundryResponseDtoList.add(orderMapper.toResponse(l));
            }
        }

        Payment payment = paymentRepository.findByOrderId(order.getId());

        if (progress.getStatus().equals(ProgressStatus.수거준비중) || progress.getStatus().equals(수거진행중) || progress.getStatus().equals(ProgressStatus.수거완료) || progress.getStatus().equals(ProgressStatus.세탁준비중)) {
            return orderMapper.toResponse(progress, laundryResponseDtoList);
        }

        return orderMapper.toResponse(progress, laundryResponseDtoList, payment);
    }

    @Transactional
    public void updateProgress(User user, String status, Long progressId) {
        Progress progress = progressRepository.findById(progressId).orElseThrow(() -> new CustomException(PROGRESS_NOT_FOUND));

        if (String.valueOf(ProgressStatus.배송완료).equals(status)) {
            Order order = orderRepository.findByProgress(progress);
            order.doneOrder();
        }

        progress.update(ProgressStatus.valueOf(status));

        switch (ProgressStatus.valueOf(status)) {
            case 수거진행중:
                createAlarm(user, AlarmType.PickupStart);
                break;
            case 배송진행중:
                createAlarm(user, AlarmType.DeliveryStart);
                break;
            case 배송완료:
                createAlarm(user, AlarmType.DeliveryDone);
                break;
            default:
                break;
        }
    }

    @Transactional
    public void deleteOrder(User user, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));

        //권한체크
        if (!order.getUser().getId().equals(user.getId())) {
            throw new CustomException(AUTHORIZATION_DELETE_FAIL);
        }

        Progress progress = progressRepository.findByOrder(order);

        if (!progress.getStatus().equals(ProgressStatus.수거준비중)) {
            throw new CustomException(LAUNDRY_PICKUP_START_ERROR);
        }

        progressRepository.delete(progress);
        orderRepository.delete(order);
    }

    private void createAlarm(User user, AlarmType alarmType) {
        Alarm alarm = alarmMapper.toAlarm(user, alarmType);
        alarmRepository.save(alarm);
    }
}
