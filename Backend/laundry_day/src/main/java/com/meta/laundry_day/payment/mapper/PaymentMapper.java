package com.meta.laundry_day.payment.mapper;

import com.meta.laundry_day.order.dto.LaundryResponseDto;
import com.meta.laundry_day.order.entity.Order;
import com.meta.laundry_day.order.entity.Progress;
import com.meta.laundry_day.payment.dto.CardResponseDto;
import com.meta.laundry_day.payment.dto.PaymentResponseDto;
import com.meta.laundry_day.payment.dto.PointResponseDto;
import com.meta.laundry_day.payment.entity.Card;
import com.meta.laundry_day.payment.entity.Payment;
import com.meta.laundry_day.payment.entity.PaymentDtails;
import com.meta.laundry_day.payment.entity.PointDivision;
import com.meta.laundry_day.payment.entity.UserPoint;
import com.meta.laundry_day.user.entity.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentMapper {

    public CardResponseDto toResponse(Card c) {
        return CardResponseDto.builder()
                .cardId(c.getId())
                .mainCard(c.getMainCard())
                .cardNumber(c.getCardNumber())
                .cardCompany(c.getCardCompany())
                .build();
    }

    public PaymentResponseDto toResponse(Order o, PaymentDtails paymentDtails, Progress progress, List<LaundryResponseDto> laundryResponseDtoList) throws ParseException {
        // JSONParser로 JSONObject로 변환
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(paymentDtails.getReceipt());
        return PaymentResponseDto.builder()
                .laundryType(String.valueOf(o.getLaundryType()))
                .washingMethod(o.getWashingMethod())
                .address(o.getAddress())
                .orderRequest(o.getOrderRequest())
                .orderStatus(o.getStatus())
                .orderCreatedAt(String.valueOf(o.getCreatedAt()))
                .progressStatus(String.valueOf(progress.getStatus()))
                .baiscAmount(paymentDtails.getBasicAmount())
                .deliveryFee(paymentDtails.getDeliveryFee())
                .discountRate(paymentDtails.getDiscountRate())
                .usePoint(paymentDtails.getUsePoint())
                .totalAmount(paymentDtails.getTotalAmount())
                .laundryResponseDtoList(laundryResponseDtoList)
                .paymentCreatedAt(String.valueOf(paymentDtails.getCreatedAt()))
                .cardNumber(paymentDtails.getCardNumber())
                .cardCompany(paymentDtails.getCardCompany())
                .receipt(jsonObject)
                .build();
    }

    public PointResponseDto toResponse(UserPoint p) {
        return PointResponseDto.builder()
                .UserPointId(p.getId())
                .division(String.valueOf(p.getDivision()))
                .point(p.getPoint())
                .createdAt(String.valueOf(p.getCreatedAt()))
                .build();
    }

    public Card toCard(JSONObject jsonObject, User user, String cardResponseData) {
        return Card.builder()
                .mainCard(0)
                .cardNumber(String.valueOf(jsonObject.get("cardNumber")))
                .cardCompany(String.valueOf(jsonObject.get("cardCompany")))
                .customerKey(String.valueOf(jsonObject.get("customerKey")))
                .billingKey(String.valueOf(jsonObject.get("billingKey")))
                .user(user)
                .cardResponseData(cardResponseData)
                .build();
    }

    public Payment toPayment(Long id, Long amount, Double usePoint, Double discountRate, Long totalStablePrice, Long totalSurcharge, Long deliveryFee, Double totalAmount) {
        return Payment.builder()
                .orderId(id)
                .usePoint(usePoint)
                .amount(amount)
                .totalStablePrice(totalStablePrice)
                .totalStablePriceSurcharge(totalSurcharge)
                .discountRate(discountRate)
                .deliveryFee(deliveryFee)
                .totalAmount(totalAmount)
                .build();
    }

    public PaymentDtails toPaymentDetails(User user, Order order, Card card, Payment payment, String body) throws ParseException {
        return PaymentDtails.builder()
                .userId(user.getId())
                .basicAmount(payment.getAmount())
                .usePoint(payment.getUsePoint())
                .discountRate(payment.getDiscountRate())
                .deliveryFee(payment.getDeliveryFee())
                .totalAmount(payment.getTotalAmount())
                .customerKey(card.getCustomerKey())
                .cardNumber(card.getCardNumber())
                .cardCompany(card.getCardCompany())
                .receipt(body)
                .card(card)
                .order(order)
                .build();
    }

    public UserPoint toAddPoint(User user, Double addPoint) {
        return UserPoint.builder()
                .point(addPoint)
                .division(PointDivision.적립)
                .user(user)
                .build();
    }

    public UserPoint toUsePoint(User user, Double usePoint) {
        return UserPoint.builder()
                .point(usePoint)
                .division(PointDivision.사용)
                .user(user)
                .build();
    }
}
