package com.meta.laundry_day.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProgressResponeDto {
    private Long progressId;
    private String status;
    private Long stablePrice;
    private Long surcharge;
    private Long deliveryFee;
    private Long usePoint;
    private List<LaundryResponseDto> laundryResponseDtoList;
    private String createAt;
}
