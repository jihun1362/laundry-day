package com.meta.laundry_day.payment.dto;

import com.meta.laundry_day.order.dto.LaundryResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.json.simple.JSONObject;

import java.util.List;

@Getter
@Builder
public class PaymentResponseDto {
    private String laundryType;
    private String washingMethod;
    private String address;
    private String orderRequest;
    private int orderStatus;
    private String orderCreatedAt;
    private String progressStatus;
    private Long baiscAmount;
    private Long deliveryFee;
    private Double discountRate;
    private Double usePoint;
    private Double totalAmount;
    private List<LaundryResponseDto> laundryResponseDtoList;
    private String paymentCreatedAt;
    private String cardNumber;
    private String cardCompany;
    private JSONObject receipt;
}
