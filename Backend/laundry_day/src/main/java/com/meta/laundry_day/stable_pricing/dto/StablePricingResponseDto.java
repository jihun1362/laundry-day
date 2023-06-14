package com.meta.laundry_day.stable_pricing.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StablePricingResponseDto {
    private Long stablePricingId;
    private Long washingTypeId;
    private String clothesType;
    private Long price;
    private String createAt;
}
