package com.meta.laundry_day.address_details.service;

import com.meta.laundry_day.address_details.dto.AddressRequestDto;
import com.meta.laundry_day.address_details.dto.AddressResponseDto;
import com.meta.laundry_day.address_details.entity.AddressDetails;
import com.meta.laundry_day.address_details.mapper.AddressMapper;
import com.meta.laundry_day.address_details.repository.AddressDetailRepository;
import com.meta.laundry_day.common.exception.CustomException;
import com.meta.laundry_day.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.meta.laundry_day.common.message.ErrorCode.ADDRESS_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AddressDetailsService {
    private final AddressDetailRepository addressDetailRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public void updateAddress(User user, AddressRequestDto addressRequestDto) {

        //주소 있는지 체크해서 없으면 생성 있으면 업뎃
        AddressDetails address = addressDetailRepository.findByUser(user);
        if (address != null){
            address.update(addressRequestDto);
            return;
        }

        AddressDetails addressDetails = addressMapper.toAddressDetails(addressRequestDto,user);
        addressDetailRepository.save(addressDetails);
    }

    @Transactional(readOnly = true)
    public AddressResponseDto addressRequest(User user) {
        AddressDetails address = addressDetailRepository.findByUser(user);
        if (address == null){
            throw new CustomException(ADDRESS_NOT_FOUND);
        }
        return addressMapper.toResponse(address);
    }
}
