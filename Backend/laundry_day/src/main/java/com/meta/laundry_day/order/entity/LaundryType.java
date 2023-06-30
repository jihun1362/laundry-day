package com.meta.laundry_day.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LaundryType {
    common("일반 세탁 서비스"),
    day("당일 세탁 서비스");

    private String type;
}
