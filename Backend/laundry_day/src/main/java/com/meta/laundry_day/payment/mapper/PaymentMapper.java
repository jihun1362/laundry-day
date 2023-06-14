package com.meta.laundry_day.payment.mapper;

import com.meta.laundry_day.payment.dto.CardResponseDto;
import com.meta.laundry_day.payment.entity.Card;
import com.meta.laundry_day.user.entity.User;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public CardResponseDto toResponse(Card c) {
        return CardResponseDto.builder()
                .cardId(c.getId())
                .mainCard(c.getMainCard())
                .cardNumber(c.getCardNumber())
                .cardCompany(c.getCardCompany())
                .build();
    }

    public Card toCard(JSONObject jsonObject, User user, String cardResponseData) {
        return Card.builder()
                .mainCard(0)
                .cardNumber(String.valueOf(jsonObject.get("cardNumber")))
                .cardCompany(String.valueOf(jsonObject.get("cardCompany")))
                .customerKey(String.valueOf(jsonObject.get("customerKey")))
                .billingKey(String.valueOf(jsonObject.get("billingKey")))
                .user(user)
                .cardResponseData(cardResponseData)
                .build();
    }
}
