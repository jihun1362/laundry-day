package com.meta.laundry_day.payment.service;

import com.meta.laundry_day.alarm.entity.Alarm;
import com.meta.laundry_day.alarm.entity.AlarmType;
import com.meta.laundry_day.alarm.mapper.AlarmMapper;
import com.meta.laundry_day.alarm.repository.AlarmRepository;
import com.meta.laundry_day.common.exception.CustomException;
import com.meta.laundry_day.order.dto.LaundryResponseDto;
import com.meta.laundry_day.order.entity.Laundry;
import com.meta.laundry_day.order.entity.Order;
import com.meta.laundry_day.order.mapper.OrderMapper;
import com.meta.laundry_day.order.repository.LaundryRepository;
import com.meta.laundry_day.order.repository.OrderRepository;
import com.meta.laundry_day.payment.dto.CardRequestDto;
import com.meta.laundry_day.payment.dto.CardResponseDto;
import com.meta.laundry_day.payment.dto.PaymentResponseDto;
import com.meta.laundry_day.payment.dto.PointResponseDto;
import com.meta.laundry_day.payment.entity.Card;
import com.meta.laundry_day.payment.entity.Payment;
import com.meta.laundry_day.payment.entity.PaymentDtails;
import com.meta.laundry_day.payment.entity.UserPoint;
import com.meta.laundry_day.payment.mapper.PaymentMapper;
import com.meta.laundry_day.payment.repository.CardRepository;
import com.meta.laundry_day.payment.repository.PaymentDtailsRepository;
import com.meta.laundry_day.payment.repository.PaymentRepository;
import com.meta.laundry_day.payment.repository.UserPointRepository;
import com.meta.laundry_day.user.entity.User;
import com.meta.laundry_day.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static com.meta.laundry_day.common.message.ErrorCode.AUTHORIZATION_DELETE_FAIL;
import static com.meta.laundry_day.common.message.ErrorCode.AUTHORIZATION_FAIL;
import static com.meta.laundry_day.common.message.ErrorCode.CARD_INFORM_NOT_FOUNT_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.CARD_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.ORDER_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.REP_CARD_DESIGNATE_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.USER_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final CardRepository cardRepository;
    private final PaymentMapper paymentMapper;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentDtailsRepository paymentDtailsRepository;
    private final UserPointRepository pointRepository;
    private final UserRepository userRepository;
    private final LaundryRepository laundryRepository;
    private final OrderMapper orderMapper;
    private final AlarmMapper alarmMapper;
    private final AlarmRepository alarmRepository;

    @Transactional
    public void createCard(User user, CardRequestDto requestDto) throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/billing/authorizations/issue"))
                .header("Authorization", "Basic dGVzdF9za196WExrS0V5cE5BcldtbzUwblgzbG1lYXhZRzVSOg==")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"authKey\":\"" + requestDto.getAuthKey() + "\",\"customerKey\":\"" + requestDto.getCustomerKey() + "\"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // JSONParser로 JSONObject로 변환
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.body());

        //카드 정보 없으면 에러
        if (jsonObject.get("billingKey") == null || jsonObject.get("customerKey") == null) {
            throw new CustomException(CARD_INFORM_NOT_FOUNT_ERROR);
        }

        Card card = paymentMapper.toCard(jsonObject, user, response.body());

        cardRepository.save(card);
    }

    @Transactional(readOnly = true)
    public List<CardResponseDto> cardList(User user) {
        List<Card> cards = cardRepository.findAllByUser(user);
        List<CardResponseDto> cardResponseDtoList = new ArrayList<>();
        for (Card c : cards) {
            cardResponseDtoList.add(paymentMapper.toResponse(c));
        }
        return cardResponseDtoList;
    }

    @Transactional
    public void deleteCard(User user, Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CustomException(CARD_NOT_FOUND));

        //권한체크
        if (!card.getUser().getId().equals(user.getId())) {
            throw new CustomException(AUTHORIZATION_DELETE_FAIL);
        }

        cardRepository.deleteById(cardId);
    }

    @Transactional
    public void designateCard(User user, Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CustomException(CARD_NOT_FOUND));

        //권한체크
        if (!card.getUser().getId().equals(user.getId())) {
            throw new CustomException(AUTHORIZATION_FAIL);
        }

        List<Card> cards = cardRepository.findAllByUser(user);
        for (Card c : cards) {
            if (c.getMainCard() == 1) {
                c.delRepresentativeDesignate();
            }
        }

        card.representativeDesignate();

        //대표카드가 혹시나 두개 안되게 체크
        List<Card> cards2 = cardRepository.findAllByUser(user);
        int count = 0;
        for (Card c : cards2) {
            if (c.getMainCard() == 1) {
                count++;
            }
        }
        if (count == 2) {
            throw new CustomException(REP_CARD_DESIGNATE_ERROR);
        }
    }

    @Transactional
    public void createPayment(User user) throws IOException, InterruptedException, ParseException {
        List<Order> orders = orderRepository.findAllByUser(user);
        Order order = null;
        for (Order o : orders) {
            if (o.getPaymentDone() == 1) order = o;
        }

        if (order == null) {
            throw new CustomException(ORDER_NOT_FOUND);
        }

        Card card = cardRepository.findByUserAndMainCard(user, 1);

        Payment payment = paymentRepository.findByOrderId(order.getId());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/billing/" + card.getBillingKey()))
                .header("Authorization", "Basic dGVzdF9za196WExrS0V5cE5BcldtbzUwblgzbG1lYXhZRzVSOg==")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"customerKey\":\"" + card.getCustomerKey() + "\",\"amount\":" + payment.getTotalAmount() + ",\"orderId\":\"" + "orderIdT22" + order.getId() + "\",\"orderName\":\"" + order.getWashingMethod() + " 세탁 주문" + "\",\"customerEmail\":\"" + user.getEmail() + "\",\"customerName\":\"" + user.getNickname() + "\",\"taxFreeAmount\":" + 0 + "}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        PaymentDtails paymentDtails = paymentMapper.toPaymentDetails(user, order, card, payment, response.body());

        paymentDtailsRepository.save(paymentDtails);
        order.setPaymentDtails(paymentDtails);
        order.setPaymentDone();

        User user1 = userRepository.findById(user.getId()).orElseThrow(() -> new CustomException(USER_NOT_FOUND_ERROR));

        if (payment.getUsePoint() > 0) {
            UserPoint saveUsePoint = paymentMapper.toUsePoint(user1, payment.getUsePoint());
            pointRepository.save(saveUsePoint);
            user1.usePoint(payment.getUsePoint());
        }

        Double addPoint = payment.getTotalAmount() * 0.01;
        UserPoint saveAddPoint = paymentMapper.toAddPoint(user1, addPoint);
        pointRepository.save(saveAddPoint);
        user1.addPoint(addPoint);

        Alarm alarm = alarmMapper.toAlarm(user, AlarmType.CompletePayment);
        alarmRepository.save(alarm);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> paymentDtailsList(User user) throws ParseException {
        List<Order> orders = orderRepository.findAllByUserAndPaymentDoneOrderByCreatedAtDesc(user, 0);
        List<PaymentResponseDto> paymentResponseDtos = new ArrayList<>();
        for (Order o : orders) {
            List<LaundryResponseDto> laundryResponseDtoList = new ArrayList<>();
            List<Laundry> laundrys = laundryRepository.findAllByProgress(o.getProgress());
            for (Laundry l : laundrys) {
                laundryResponseDtoList.add(orderMapper.toResponse(l));
            }
            paymentResponseDtos.add(paymentMapper.toResponse(o, o.getPaymentDtails(), o.getProgress(), laundryResponseDtoList));
        }
        return paymentResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<PointResponseDto> pointList(User user) {
        List<UserPoint> points = pointRepository.findAllByUserOrderByCreatedAtDesc(user);
        List<PointResponseDto> pointResponseDtoList = new ArrayList<>();
        for (UserPoint p : points) {
            pointResponseDtoList.add(paymentMapper.toResponse(p));
        }
        return pointResponseDtoList;
    }

    public Long deliveryFeeCheck(Long amount) {
        if (amount >= 30000) {
            return 0L;
        } else if (amount >= 15000) {
            return 3000L;
        } else
            return 5000L;
    }
}
