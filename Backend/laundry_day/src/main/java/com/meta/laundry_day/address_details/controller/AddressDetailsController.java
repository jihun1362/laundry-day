package com.meta.laundry_day.address_details.controller;

import com.meta.laundry_day.address_details.dto.AddressRequestDto;
import com.meta.laundry_day.address_details.dto.AddressResponseDto;
import com.meta.laundry_day.address_details.service.AddressDetailsService;
import com.meta.laundry_day.common.dto.ResponseDto;
import com.meta.laundry_day.common.message.ResultCode;
import com.meta.laundry_day.security.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.meta.laundry_day.common.message.ResultCode.ADDRESS_CREATE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.ADDRESS_DELETE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.ADDRESS_LIST_REQUEST_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.ADDRESS_MODIFY_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressDetailsController {
    private final AddressDetailsService addressDetailsService;

    @PostMapping("")
    public ResponseEntity<ResponseDto<ResultCode>> addressCreate(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @RequestBody AddressRequestDto addressRequestDto) {
        addressDetailsService.addressCreate(userDetails.getUser(), addressRequestDto);
        return ResponseEntity.status(201)
                .body(new ResponseDto<>(ADDRESS_CREATE_SUCCESS, null));
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<AddressResponseDto>>> addressList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(ADDRESS_LIST_REQUEST_SUCCESS, addressDetailsService.addressList(userDetails.getUser())));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ResponseDto<ResultCode>> updateAddress(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @RequestBody AddressRequestDto addressRequestDto,
                                                                 @PathVariable Long addressId) {
        addressDetailsService.updateAddress(userDetails.getUser(), addressRequestDto, addressId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(ADDRESS_MODIFY_SUCCESS, null));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ResponseDto<ResultCode>> deleteAddress(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @PathVariable Long addressId) {
        addressDetailsService.deleteAddress(userDetails.getUser(), addressId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(ADDRESS_DELETE_SUCCESS, null));
    }

}
