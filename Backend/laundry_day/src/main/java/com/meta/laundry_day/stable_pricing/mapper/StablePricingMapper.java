package com.meta.laundry_day.stable_pricing.mapper;

import com.meta.laundry_day.stable_pricing.dto.StablePricingListResponseDto;
import com.meta.laundry_day.stable_pricing.dto.StablePricingRequestDto;
import com.meta.laundry_day.stable_pricing.dto.StablePricingResponseDto;
import com.meta.laundry_day.stable_pricing.entity.StablePricing;
import com.meta.laundry_day.stable_pricing.entity.WashingType;
import com.meta.laundry_day.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StablePricingMapper {

    public StablePricingResponseDto toResponse(StablePricing s) {
        return StablePricingResponseDto.builder()
                .stablePricingId(s.getId())
                .washingTypeId(s.getWashingType().getId())
                .clothesType(s.getClothesType())
                .price(s.getPrice())
                .createAt(String.valueOf(s.getCreatedAt()))
                .build();
    }

    public StablePricingListResponseDto toResponse(WashingType w, List<StablePricingResponseDto> stablePricingResponseDtos) {
        return StablePricingListResponseDto.builder()
                .washingTypeId(w.getId())
                .typeName(w.getTypeName())
                .createAt(String.valueOf(w.getCreatedAt()))
                .StablePricingList(stablePricingResponseDtos)
                .build();
    }

    public WashingType toWashingType(String typeName, User user) {
        return WashingType.builder()
                .typeName(typeName)
                .user(user)
                .build();
    }

    public StablePricing toStablePricing(StablePricingRequestDto stablePricingRequestDto, User user, WashingType washingType) {
        return StablePricing.builder()
                .washingType(washingType)
                .clothesType(stablePricingRequestDto.getClothesType())
                .price(stablePricingRequestDto.getPrice())
                .user(user)
                .build();
    }

}
