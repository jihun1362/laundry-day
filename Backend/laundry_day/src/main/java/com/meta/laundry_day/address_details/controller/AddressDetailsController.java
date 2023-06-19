package com.meta.laundry_day.address_details.controller;

import com.meta.laundry_day.address_details.dto.AddressRequestDto;
import com.meta.laundry_day.address_details.dto.AddressResponseDto;
import com.meta.laundry_day.address_details.service.AddressDetailsService;
import com.meta.laundry_day.common.dto.ResponseDto;
import com.meta.laundry_day.common.message.ResultCode;
import com.meta.laundry_day.security.util.UserDetailsImpl;
import com.meta.laundry_day.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.meta.laundry_day.common.message.ResultCode.ADDRESS_CREATE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.ADDRESS_REQUEST_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressDetailsController {
    private final AddressDetailsService addressDetailsService;

    @PostMapping("")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<ResultCode>> updateAddress(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @RequestBody AddressRequestDto addressRequestDto) {
        addressDetailsService.updateAddress(userDetails.getUser(), addressRequestDto);
        return ResponseEntity.status(201)
                .body(new ResponseDto<>(ADDRESS_CREATE_SUCCESS, null));
    }

    @GetMapping("")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<AddressResponseDto>> addressRequest(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(ADDRESS_REQUEST_SUCCESS, addressDetailsService.addressRequest(userDetails.getUser())));
    }
}
