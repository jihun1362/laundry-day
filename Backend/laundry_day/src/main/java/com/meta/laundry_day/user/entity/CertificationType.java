package com.meta.laundry_day.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CertificationType {
    Phone("핸드폰"),
    Email("이메일"),
    ;
    private final String type;
}
