package com.meta.laundry_day.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProgressStatus {
    PreparingForPickup("수거준비중"),
    PickupInProgress("수거진행중"),
    PickupComplete("수거완료"),
    WashingInProgress("세탁진행중"),
    WashingDone("세탁완료"),
    PreparingForDelivery("배송준비중"),
    DeliveryCompleted("배송완료"),
    ;

    private final String status;
}
