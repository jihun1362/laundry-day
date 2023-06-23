package com.meta.laundry_day.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderReaponseDto {
    private Long orderId;
    private Long userId;
    private String username;
    private String laundryType;
    private String washingMethod;
    private String address;
    private String orderRequest;
    private int status;
    private int paymentDone;
    private String createAt;
}
