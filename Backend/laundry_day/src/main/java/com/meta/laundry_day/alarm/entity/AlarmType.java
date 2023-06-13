package com.meta.laundry_day.alarm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
    PickupStart("수거시작", ""),
    DeliveryStart("배송시작", ""),
    WashingDone("세탁완료", ""),
    Event("이벤트", ""),
    PaymentRequest("결제요청", "");

    private final String type;
    private final String content;
}
