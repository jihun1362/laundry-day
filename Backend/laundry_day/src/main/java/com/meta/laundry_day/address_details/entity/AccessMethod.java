package com.meta.laundry_day.address_details.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccessMethod {
    CommonEntrancePassword("공동현관 비밀번호"),
    FreeEntry("자유 출입"),
    CallTheGuardRoom("경비실 호출"),
    HouseholdCall("세대 호출"),
    Etc("기타");

    private final String type;
}
