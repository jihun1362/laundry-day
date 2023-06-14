package com.meta.laundry_day.payment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardResponseDto {
    private Long cardId;
    private int mainCard;
    private String cardNumber;
    private String cardCompany;
}
