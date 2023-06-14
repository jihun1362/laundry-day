package com.meta.laundry_day.stable_pricing.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StablePricingListResponseDto {
    private Long washingTypeId;
    private String typeName;
    private String createAt;
    private List<StablePricingResponseDto> StablePricingList;
}
