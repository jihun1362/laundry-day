package com.meta.laundry_day.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LaundryType {
    GeneralLaundry("일반세탁"),
    SamedayLaundry("당일세탁")
    ;

    private final String type;
}
