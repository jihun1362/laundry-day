package com.meta.laundry_day.inquiry.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InquiryType {
    PickupDeliveryInquiries("수거/배송 문의"),
    PaymentInquiry("결제 문의"),
    LaundryInquiry("세탁 문의"),
    EventInquiry("이벤트 문의"),
    MemberServiceErrorInquiry("회원/서비스/오류 문의"),
    ;

    private final String type;
}
