package com.meta.laundry_day.stable_pricing.controller;

import com.meta.laundry_day.common.dto.ResponseDto;
import com.meta.laundry_day.common.message.ResultCode;
import com.meta.laundry_day.security.util.UserDetailsImpl;
import com.meta.laundry_day.stable_pricing.dto.StablePricingListResponseDto;
import com.meta.laundry_day.stable_pricing.dto.StablePricingRequestDto;
import com.meta.laundry_day.stable_pricing.service.StablePricingService;
import com.meta.laundry_day.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.meta.laundry_day.common.message.ResultCode.STABLEPRICING_CREATE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.STABLEPRICING_DELETE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.STABLEPRICING_LIST_REQUEST_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.STABLEPRICING_MODIFY_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.WASHINFTYPE_CREATE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.WASHINFTYPE_DELETE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.WASHINFTYPE_MODIFY_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stable-price")
public class StablePricingController {
    private final StablePricingService stablePricingService;

    @PostMapping("/washingtype")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> createWashingType(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                     @RequestParam String typeName) {
        stablePricingService.createWashingType(userDetails.getUser(), typeName);
        return ResponseEntity.status(201)
                .body(new ResponseDto<>(WASHINFTYPE_CREATE_SUCCESS, null));
    }

    @PutMapping("/washingtype/{washingtypeId}")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> updateWashingType(@RequestParam String typeName,
                                                                     @PathVariable Long washingtypeId) {
        stablePricingService.updateWashingType(typeName, washingtypeId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(WASHINFTYPE_MODIFY_SUCCESS, null));
    }

    @DeleteMapping("/washingtype/{washingtypeId}")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> deleteWashingType(@PathVariable Long washingtypeId) {
        stablePricingService.deleteWashingType(washingtypeId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(WASHINFTYPE_DELETE_SUCCESS, null));
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<StablePricingListResponseDto>>> stablePricingList() {
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(STABLEPRICING_LIST_REQUEST_SUCCESS, stablePricingService.stablePricingList()));
    }

    @PostMapping("/{washingtypeId}")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> createStablePricing(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                       @RequestBody StablePricingRequestDto responseDto,
                                                                       @PathVariable Long washingtypeId) {
        stablePricingService.createStablePricing(userDetails.getUser(), responseDto, washingtypeId);
        return ResponseEntity.status(201)
                .body(new ResponseDto<>(STABLEPRICING_CREATE_SUCCESS, null));
    }

    @PutMapping("/{stablepriceId}")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> updateStablePricing(@RequestBody StablePricingRequestDto responseDto,
                                                                       @PathVariable Long stablepriceId) {
        stablePricingService.updateStablePricing(responseDto, stablepriceId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(STABLEPRICING_MODIFY_SUCCESS, null));
    }

    @DeleteMapping("/{stablepriceId}")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> deleteStablePricing(@PathVariable Long stablepriceId) {
        stablePricingService.deleteStablePricing(stablepriceId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(STABLEPRICING_DELETE_SUCCESS, null));
    }
}
