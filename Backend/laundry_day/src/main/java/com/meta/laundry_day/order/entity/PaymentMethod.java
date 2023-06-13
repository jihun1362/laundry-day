package com.meta.laundry_day.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    AutomaticPayment("자동결제"),
    DirectPaymentAfterPricing("가격책정 후 직접결제")
    ;

    private final String type;
}
