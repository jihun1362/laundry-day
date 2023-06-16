package com.meta.laundry_day.address_details.service;

import com.meta.laundry_day.address_details.dto.AddressRequestDto;
import com.meta.laundry_day.address_details.dto.AddressResponseDto;
import com.meta.laundry_day.address_details.entity.AddressDetails;
import com.meta.laundry_day.address_details.mapper.AddressMapper;
import com.meta.laundry_day.address_details.repository.AddressDetailRepository;
import com.meta.laundry_day.common.exception.CustomException;
import com.meta.laundry_day.user.entity.User;
import com.meta.laundry_day.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.meta.laundry_day.common.message.ErrorCode.ADDRESS_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.ADMIN_SERVICE_ACCESS_BLOCK;
import static com.meta.laundry_day.common.message.ErrorCode.AUTHORIZATION_DELETE_FAIL;
import static com.meta.laundry_day.common.message.ErrorCode.AUTHORIZATION_UPDATE_FAIL;

@Service
@RequiredArgsConstructor
public class AddressDetailsService {
    private final AddressDetailRepository addressDetailRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public void addressCreate(User user, AddressRequestDto addressRequestDto) {

        //관리자는 관리만하게 접근 차단
        if (user.getRole().equals(UserRoleEnum.ADMIN)){
            throw new CustomException(ADMIN_SERVICE_ACCESS_BLOCK);
        }

        AddressDetails addressDetails = addressMapper.toAddressDetails(addressRequestDto,user);

        addressDetailRepository.save(addressDetails);
    }

    @Transactional(readOnly = true)
    public List<AddressResponseDto> addressList(User user) {
        List<AddressDetails> addressDetailsList = addressDetailRepository.findAllByUser(user);
        List<AddressResponseDto> addressResponseDtoList = new ArrayList<>();
        for (AddressDetails p : addressDetailsList) {
            addressResponseDtoList.add(addressMapper.toResponse(p));
        }
        return addressResponseDtoList;
    }

    @Transactional
    public void updateAddress(User user, AddressRequestDto addressRequestDto, Long addressId) {
        AddressDetails address = addressDetailRepository.findById(addressId).orElseThrow(()->new CustomException(ADDRESS_NOT_FOUND));

        //권한체크
        if (!address.getUser().getId().equals(user.getId())) {
            throw new CustomException(AUTHORIZATION_UPDATE_FAIL);
        }

        address.update(
                addressRequestDto.getAddress(),
                addressRequestDto.getSignificant(),
                addressRequestDto.getAccessMethod()
        );
    }

    @Transactional
    public void deleteAddress(User user, Long addressId) {
        AddressDetails address = addressDetailRepository.findById(addressId).orElseThrow(()->new CustomException(ADDRESS_NOT_FOUND));

        //권한체크
        if (!address.getUser().getId().equals(user.getId())) {
            throw new CustomException(AUTHORIZATION_DELETE_FAIL);
        }

        addressDetailRepository.deleteById(addressId);
    }
}
