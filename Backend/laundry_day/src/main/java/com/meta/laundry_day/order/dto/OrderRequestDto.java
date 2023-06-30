package com.meta.laundry_day.order.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private String laundryType;
    private String washingMethod;
    private String orderRequest;
    private int usePointCheck;
}
