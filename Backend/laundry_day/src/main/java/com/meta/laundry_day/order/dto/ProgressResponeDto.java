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
    private Long totalSurcharge;
    private Long deliveryFee;
    private Double discountRate;
    private Double usePoint;
    private List<LaundryResponseDto> laundryResponseDtoList;
    private String createAt;
}
