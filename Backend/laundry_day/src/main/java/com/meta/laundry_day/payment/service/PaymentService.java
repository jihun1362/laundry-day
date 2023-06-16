package com.meta.laundry_day.payment.service;

import com.meta.laundry_day.common.exception.CustomException;
import com.meta.laundry_day.payment.dto.CardRequestDto;
import com.meta.laundry_day.payment.dto.CardResponseDto;
import com.meta.laundry_day.payment.entity.Card;
import com.meta.laundry_day.payment.mapper.PaymentMapper;
import com.meta.laundry_day.payment.repository.CardRepository;
import com.meta.laundry_day.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static com.meta.laundry_day.common.message.ErrorCode.ADDRESS_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.AUTHORIZATION_DELETE_FAIL;
import static com.meta.laundry_day.common.message.ErrorCode.AUTHORIZATION_UPDATE_FAIL;
import static com.meta.laundry_day.common.message.ErrorCode.CARD_INFORM_NOT_FOUNT_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.CARD_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.REP_CARD_DESIGNATE_ERROR;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final CardRepository cardRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public void createCard(User user, CardRequestDto requestDto) throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/billing/authorizations/issue"))
                .header("Authorization", "Basic dGVzdF9za196WExrS0V5cE5BcldtbzUwblgzbG1lYXhZRzVSOg==")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"authKey\":\"" + requestDto.getAuthKey() + "\",\"customerKey\":\"" + requestDto.getCustomerKey() + "\"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // JSONParser로 JSONObject로 변환
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.body());

        //카드 정보 없으면 에러
        if (jsonObject.get("billingKey") == null || jsonObject.get("customerKey") == null) {
            throw new CustomException(CARD_INFORM_NOT_FOUNT_ERROR);
        }

        Card card = paymentMapper.toCard(jsonObject, user, response.body());

        cardRepository.save(card);
    }

    @Transactional(readOnly = true)
    public List<CardResponseDto> cardList(User user) {
        List<Card> cards = cardRepository.findAllByUser(user);
        List<CardResponseDto> cardResponseDtoList = new ArrayList<>();
        for (Card c : cards) {
            cardResponseDtoList.add(paymentMapper.toResponse(c));
        }
        return cardResponseDtoList;
    }

    @Transactional
    public void deleteCard(User user, Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CustomException(ADDRESS_NOT_FOUND));

        //권한체크
        if (!card.getUser().getId().equals(user.getId())) {
            throw new CustomException(AUTHORIZATION_UPDATE_FAIL);
        }

        cardRepository.deleteById(cardId);
    }

    @Transactional
    public void designateCard(User user, Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CustomException(CARD_NOT_FOUND));

        //권한체크
        if (!card.getUser().getId().equals(user.getId())) {
            throw new CustomException(AUTHORIZATION_DELETE_FAIL);
        }

        List<Card> cards = cardRepository.findAllByUser(user);
        for (Card c : cards) {
            if (c.getMainCard() == 1) {
                c.delRepresentativeDesignate();
            }
        }

        card.representativeDesignate();

        //대표카드가 혹시나 두개 안되게 체크
        List<Card> cards2 = cardRepository.findAllByUser(user);
        int count = 0;
        for (Card c : cards2) {
            if (c.getMainCard() == 1) {
                count++;
            }
        }
        if (count == 2) {
            throw new CustomException(REP_CARD_DESIGNATE_ERROR);
        }
    }

    public Long deliveryFeeCheck(Long amount) {
        if (amount >= 30000) {
            return 0L;
        } else if (amount >= 15000) {
            return 3000L;
        } else
            return 5000L;
    }
}
