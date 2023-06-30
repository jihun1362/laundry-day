package com.meta.laundry_day.stable_pricing.service;

import com.meta.laundry_day.common.exception.CustomException;
import com.meta.laundry_day.stable_pricing.dto.StablePricingListResponseDto;
import com.meta.laundry_day.stable_pricing.dto.StablePricingRequestDto;
import com.meta.laundry_day.stable_pricing.dto.StablePricingResponseDto;
import com.meta.laundry_day.stable_pricing.entity.StablePricing;
import com.meta.laundry_day.stable_pricing.entity.WashingType;
import com.meta.laundry_day.stable_pricing.mapper.StablePricingMapper;
import com.meta.laundry_day.stable_pricing.repository.StablePricingRepository;
import com.meta.laundry_day.stable_pricing.repository.WashingTypeRepository;
import com.meta.laundry_day.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.meta.laundry_day.common.message.ErrorCode.STABLEPRICING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StablePricingService {
    private final WashingTypeRepository washingTypeRepository;
    private final StablePricingRepository stablePricingRepository;
    private final StablePricingMapper stablePricingMapper;

    @Transactional
    public void createWashingType(User user, String typeName) {
        WashingType washingType = stablePricingMapper.toWashingType(typeName, user);

        washingTypeRepository.save(washingType);
    }

    @Transactional
    public void updateWashingType(String typeName, Long washingtypeId) {
        WashingType washingType = washingTypeRepository.findById(washingtypeId).orElseThrow(() -> new CustomException(STABLEPRICING_NOT_FOUND));

        washingType.update(typeName);
    }

    @Transactional
    public void deleteWashingType(Long washingtypeId) {
        washingTypeRepository.findById(washingtypeId).orElseThrow(() -> new CustomException(STABLEPRICING_NOT_FOUND));

        List<StablePricing> stablePricingList = stablePricingRepository.findAllByWashingTypeId(washingtypeId);
        if (!stablePricingList.isEmpty()) stablePricingRepository.deleteAllByInQuery(stablePricingList);

        washingTypeRepository.deleteById(washingtypeId);
    }

    @Transactional
    public void createStablePricing(User user, StablePricingRequestDto responseDto, Long washingtypeId) {
        WashingType washingType = washingTypeRepository.findById(washingtypeId).orElseThrow(() -> new CustomException(STABLEPRICING_NOT_FOUND));

        StablePricing stablePricing = stablePricingMapper.toStablePricing(responseDto, user, washingType);

        stablePricingRepository.save(stablePricing);
    }

    @Transactional(readOnly = true)
    public List<StablePricingListResponseDto> stablePricingList() {
        List<WashingType> washingTypeList = washingTypeRepository.findAll();
        List<StablePricingListResponseDto> stablePricingListResponseDtos = new ArrayList<>();
        for (WashingType w : washingTypeList) {
            List<StablePricing> stablePricingList = stablePricingRepository.findAllByWashingTypeId(w.getId());
            List<StablePricingResponseDto> stablePricingResponseDtos = new ArrayList<>();
            for (StablePricing s : stablePricingList) {
                stablePricingResponseDtos.add(stablePricingMapper.toResponse(s));
            }
            stablePricingListResponseDtos.add(stablePricingMapper.toResponse(w, stablePricingResponseDtos));
        }
        return stablePricingListResponseDtos;
    }

    @Transactional
    public void updateStablePricing(StablePricingRequestDto responseDto, Long stablepriceId) {
        StablePricing stablePricing = stablePricingRepository.findById(stablepriceId).orElseThrow(() -> new CustomException(STABLEPRICING_NOT_FOUND));

        stablePricing.update(
                responseDto.getClothesType(),
                responseDto.getPrice()
        );
    }

    @Transactional
    public void deleteStablePricing(Long stablepriceId) {
        stablePricingRepository.findById(stablepriceId).orElseThrow(() -> new CustomException(STABLEPRICING_NOT_FOUND));

        stablePricingRepository.deleteById(stablepriceId);
    }
}
