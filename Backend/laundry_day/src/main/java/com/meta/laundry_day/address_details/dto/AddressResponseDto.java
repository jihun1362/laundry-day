package com.meta.laundry_day.address_details.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddressResponseDto {
    private Long addressDtailsId;
    private Long userId;
    private String address;
    private String accessMethod;
    private String significant;
    private String createAt;
}
