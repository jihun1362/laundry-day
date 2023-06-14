package com.meta.laundry_day.address_details.mapper;

import com.meta.laundry_day.address_details.dto.AddressRequestDto;
import com.meta.laundry_day.address_details.dto.AddressResponseDto;
import com.meta.laundry_day.address_details.entity.AddressDetails;
import com.meta.laundry_day.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressResponseDto toResponse(AddressDetails addressDetails) {
        return AddressResponseDto.builder()
                .addressDtailsId(addressDetails.getId())
                .userId(addressDetails.getUser().getId())
                .address(addressDetails.getAddress())
                .accessMethod(addressDetails.getAccessMethod())
                .significant(addressDetails.getSignificant())
                .createAt(String.valueOf(addressDetails.getCreatedAt()))
                .build();
    }

    public AddressDetails toAddressDetails(AddressRequestDto addressRequestDto, User user) {
        return AddressDetails.builder()
                .address(addressRequestDto.getAddress())
                .accessMethod(addressRequestDto.getAccessMethod())
                .significant(addressRequestDto.getSignificant())
                .user(user)
                .build();
    }
}
