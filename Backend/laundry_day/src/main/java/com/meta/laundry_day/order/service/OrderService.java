package com.meta.laundry_day.order.service;

import com.meta.laundry_day.address_details.entity.AddressDetails;
import com.meta.laundry_day.address_details.repository.AddressDetailRepository;
import com.meta.laundry_day.common.config.S3Uploader;
import com.meta.laundry_day.common.exception.CustomException;
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
import com.meta.laundry_day.payment.repository.CardRepository;
import com.meta.laundry_day.stable_pricing.entity.StablePricing;
import com.meta.laundry_day.stable_pricing.repository.StablePricingRepository;
import com.meta.laundry_day.user.entity.User;
import com.meta.laundry_day.user.entity.UserRoleEnum;
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
import static com.meta.laundry_day.common.message.ErrorCode.AUTHORIZATION_UPDATE_FAIL;
import static com.meta.laundry_day.common.message.ErrorCode.CARD_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.LAUNDRY_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.LAUNDRY_PICKUP_START_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.LAUNDRY_RESIST_DONE_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.ORDER_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.ORDER_ONLY_ONE_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.PROGRESS_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.STABLEPRICING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final AddressDetailRepository addressDetailRepository;
    private final CardRepository cardRepository;
    private final OrderRepository orderRepository;
    private final ProgressRepository progressRepository;
    private final LaundryRepository laundryRepository;
    private final StablePricingRepository stablePricingRepository;
    private final OrderMapper orderMapper;
    private final S3Uploader s3Uploader;

    @Transactional
    public void createOrder(User user, OrderRequestDto requestDto, Long addressId, Long cardId) {
        List<Order> orders = orderRepository.findAllByUser(user);
        for (Order o : orders) {
            if (o.getStatus() == 1) throw new CustomException(ORDER_ONLY_ONE_ERROR);
        }
        AddressDetails address = addressDetailRepository.findById(addressId).orElseThrow(() -> new CustomException(ADDRESS_NOT_FOUND));
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CustomException(CARD_NOT_FOUND));

        Order order = orderMapper.toOrder(requestDto, user, address, card);
        orderRepository.save(order);

        Progress progress = orderMapper.toProgress(order, user);
        progressRepository.save(progress);
        order.setProgress(progress);
    }

    @Transactional(readOnly = true)
    public OrderReaponseDto orderDetail(User user, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));

        //권한체크
        if (!order.getUser().getId().equals(user.getId())) {
            throw new CustomException(AUTHORIZATION_UPDATE_FAIL);
        }

        return orderMapper.toResponse(order);
    }

    @Transactional
    public void createLaundry(User user, LaundryRequestDto requestDto, MultipartFile image, Long stablepriceId, Long orderId) throws IOException {
        //관리자권한 아니면 예외보내기
        if (user.getRole().equals(UserRoleEnum.USER)) {
            throw new CustomException(AUTHORIZATION_FAIL);
        }

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));
        Progress progress = progressRepository.findByOrder(order);

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
    public void registLaundry(User user, Long progressId) {
        Progress progress = progressRepository.findById(progressId).orElseThrow(() -> new CustomException(PROGRESS_NOT_FOUND));

        //관리자권한 아니면 예외보내기
        if (user.getRole().equals(UserRoleEnum.USER)) {
            throw new CustomException(AUTHORIZATION_FAIL);
        }

        progress.doneRegist();
    }

    @Transactional
    public void updateLaundry(User user, String status, Long laundryId) {
        Laundry laundry = laundryRepository.findById(laundryId).orElseThrow(() -> new CustomException(LAUNDRY_NOT_FOUND));

        //관리자권한 아니면 예외보내기
        if (user.getRole().equals(UserRoleEnum.USER)) {
            throw new CustomException(AUTHORIZATION_FAIL);
        }

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

        return orderMapper.toResponse(progress, laundryResponseDtoList, user);
    }

    @Transactional
    public void updateProgress(User user, String status, Long progressId) {
        Progress progress = progressRepository.findById(progressId).orElseThrow(() -> new CustomException(PROGRESS_NOT_FOUND));

        //관리자권한 아니면 예외보내기
        if (user.getRole().equals(UserRoleEnum.USER)) {
            throw new CustomException(AUTHORIZATION_FAIL);
        }

        progress.update(ProgressStatus.valueOf(status));
    }

    @Transactional
    public void deleteOrder(User user, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));

        //권한체크
        if (!order.getUser().getId().equals(user.getId())) {
            throw new CustomException(AUTHORIZATION_DELETE_FAIL);
        }

        Progress progress = progressRepository.findByOrder(order);

        if (!progress.getStatus().equals(ProgressStatus.수거준비중)){
            throw  new CustomException(LAUNDRY_PICKUP_START_ERROR);
        }

        progressRepository.delete(progress);
        orderRepository.delete(order);
    }
}
