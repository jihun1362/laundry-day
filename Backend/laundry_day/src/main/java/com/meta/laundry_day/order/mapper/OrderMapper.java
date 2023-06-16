package com.meta.laundry_day.order.mapper;

import com.meta.laundry_day.address_details.entity.AddressDetails;
import com.meta.laundry_day.order.dto.LaundryRequestDto;
import com.meta.laundry_day.order.dto.LaundryResponseDto;
import com.meta.laundry_day.order.dto.OrderReaponseDto;
import com.meta.laundry_day.order.dto.OrderRequestDto;
import com.meta.laundry_day.order.dto.ProgressResponeDto;
import com.meta.laundry_day.order.entity.Laundry;
import com.meta.laundry_day.order.entity.LaundryStatus;
import com.meta.laundry_day.order.entity.LaundryType;
import com.meta.laundry_day.order.entity.Order;
import com.meta.laundry_day.order.entity.Progress;
import com.meta.laundry_day.order.entity.ProgressStatus;
import com.meta.laundry_day.payment.entity.Card;
import com.meta.laundry_day.payment.service.PaymentService;
import com.meta.laundry_day.stable_pricing.entity.StablePricing;
import com.meta.laundry_day.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final PaymentService paymentService;

    public OrderReaponseDto toResponse(Order orders) {
        return OrderReaponseDto.builder()
                .orderId(orders.getId())
                .laundryType(String.valueOf(orders.getLaundryType()))
                .washingMethod(orders.getWashingMethod())
                .orderRequest(orders.getOrderRequest())
                .address(orders.getAddress())
                .status(orders.getStatus())
                .createAt(String.valueOf(orders.getCreatedAt()))
                .build();
    }

    public LaundryResponseDto toResponse(Laundry l) {
        return LaundryResponseDto.builder()
                .laundryId(l.getId())
                .imageUrl(l.getImage())
                .clothesType(l.getClothesType())
                .stablePrice(l.getStablePrice())
                .surcharge(l.getSurcharge())
                .surchargeDetail(l.getSurchargeDetail())
                .status(String.valueOf(l.getStatus()))
                .createAt(String.valueOf(l.getCreatedAt()))
                .build();
    }

    public ProgressResponeDto toResponse(Progress progress, List<LaundryResponseDto> laundryResponseDtoList, User user) {
        Long stablePrice = 0L;
        Long surcharge = 0L;
        Long usePoint = user.getPoint();
        for (LaundryResponseDto l : laundryResponseDtoList) {
            stablePrice += l.getStablePrice();
            surcharge += l.getSurcharge();
        }
        if (user.getPoint() - (stablePrice + surcharge) >= 0){
            usePoint = stablePrice + surcharge;
        }

        return ProgressResponeDto.builder()
                .progressId(progress.getId())
                .status(String.valueOf(progress.getStatus()))
                .stablePrice(stablePrice)
                .surcharge(surcharge)
                .deliveryFee(paymentService.deliveryFeeCheck(stablePrice + surcharge))
                .usePoint(usePoint)
                .laundryResponseDtoList(laundryResponseDtoList)
                .createAt(String.valueOf(progress.getCreatedAt()))
                .build();
    }

    public Order toOrder(OrderRequestDto requestDto, User user, AddressDetails address, Card card) {

        return Order.builder()
                .laundryType(LaundryType.valueOf(requestDto.getLaundryType()))
                .washingMethod(requestDto.getWashingMethod())
                .address(address.getAddress())
                .orderRequest(requestDto.getOrderRequest())
                .status(1)
                .usePoint(requestDto.getUsePoint())
                .addressDetails(address)
                .user(user)
                .card(card)
                .build();
    }

    public Progress toProgress(Order order, User user) {
        return Progress.builder()
                .order(order)
                .status(ProgressStatus.수거준비중)
                .laundryRegist(1)
                .userId(user.getId())
                .build();
    }

    public Laundry toLaundry(User user, LaundryRequestDto requestDto, String imageUri, StablePricing stablePricing, Progress progress) {
        return Laundry.builder()
                .image(imageUri)
                .surchargeDetail(requestDto.getSurchargeDetail())
                .surcharge(requestDto.getSurcharge())
                .clothesType(stablePricing.getClothesType())
                .stablePrice(stablePricing.getPrice())
                .status(LaundryStatus.세탁준비중)
                .user(user)
                .progress(progress)
                .stablePricing(stablePricing)
                .build();
    }
}
