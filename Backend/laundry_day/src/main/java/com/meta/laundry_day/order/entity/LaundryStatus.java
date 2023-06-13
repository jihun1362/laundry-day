package com.meta.laundry_day.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LaundryStatus {
    PreparingForLaundry("세탁준비중"),
    WashingInProgress("세탁중"),
    WashingDone("세탁완료")
    ;

    private final String status;
}
