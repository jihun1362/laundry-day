package com.meta.laundry_day.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProgressResponeDto {
    private Long progressId;
    private String status;
    private Long totalStablePrice;
    private Double totalSurcharge;
    private Long deliveryFee;
    private Double dayDeliveryFee;
    private Double discountRate;
    private Double usePoint;
    private Double totalAmont;
    private String orderRequest;
    private List<LaundryResponseDto> laundryResponseDtoList;
    private String createAt;
}
