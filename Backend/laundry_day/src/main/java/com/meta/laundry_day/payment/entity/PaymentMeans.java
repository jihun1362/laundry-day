package com.meta.laundry_day.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMeans {
    CARD("카드"),
    KAKAOPAY("카카오페이"),
    ACCOUNTTRANSFER("계좌이체"),
    TOSSPAY("토스페이"),
    EASYPAYMENT("간편결제"),
    VIRTUALACCOUNT("가상계좌"),
    CELLPHONE("휴대폰"),
    CULTURALGIFTCERTIFICATE("문화상품권")
    ;

    private final String type;
}
