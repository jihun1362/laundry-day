package com.meta.laundry_day.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LaundryResponseDto {
    private Long laundryId;
    private String imageUrl;
    private String clothesType;
    private Long stablePrice;
    private Long surcharge;
    private String surchargeDetail;
    private String status;
    private String createAt;
}
