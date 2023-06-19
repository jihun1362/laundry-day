package com.meta.laundry_day.payment.controller;

import com.meta.laundry_day.common.dto.ResponseDto;
import com.meta.laundry_day.common.message.ResultCode;
import com.meta.laundry_day.payment.dto.CardRequestDto;
import com.meta.laundry_day.payment.dto.CardResponseDto;
import com.meta.laundry_day.payment.dto.PaymentResponseDto;
import com.meta.laundry_day.payment.dto.PointResponseDto;
import com.meta.laundry_day.payment.service.PaymentService;
import com.meta.laundry_day.security.util.UserDetailsImpl;
import com.meta.laundry_day.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.meta.laundry_day.common.message.ResultCode.CARD_CREATE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.CARD_DELETE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.CARD_LIST_REQUEST_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.PAYMENT_CREATE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.PAYMENT_LIST_REQUEST_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.POINT_LIST_REQUEST_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.REP_CARD_DESIGNATE_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/card")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<ResultCode>> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @RequestBody CardRequestDto requestDto) throws IOException, InterruptedException, org.json.simple.parser.ParseException {
        paymentService.createCard(userDetails.getUser(), requestDto);
        return ResponseEntity.status(201)
                .body(new ResponseDto<>(CARD_CREATE_SUCCESS, null));
    }

    @GetMapping("/card")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<List<CardResponseDto>>> cardList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(CARD_LIST_REQUEST_SUCCESS, paymentService.cardList(userDetails.getUser())));
    }

    @DeleteMapping("/card/{cardId}")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<ResultCode>> deleteCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @PathVariable Long cardId) {
        paymentService.deleteCard(userDetails.getUser(), cardId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(CARD_DELETE_SUCCESS, null));
    }

    @PatchMapping("/card/{cardId}")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<ResultCode>> designateCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @PathVariable Long cardId) {
        paymentService.designateCard(userDetails.getUser(), cardId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(REP_CARD_DESIGNATE_SUCCESS, null));
    }

    @PostMapping("")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> createPayment(@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException, InterruptedException, ParseException {
        paymentService.createPayment(userDetails.getUser());
        return ResponseEntity.status(201)
                .body(new ResponseDto<>(PAYMENT_CREATE_SUCCESS, null));
    }

    @GetMapping("")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<List<PaymentResponseDto>>> paymentDtailsList(@AuthenticationPrincipal UserDetailsImpl userDetails) throws ParseException {
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(PAYMENT_LIST_REQUEST_SUCCESS, paymentService.paymentDtailsList(userDetails.getUser())));
    }

    @GetMapping("/point")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<List<PointResponseDto>>> pointList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(POINT_LIST_REQUEST_SUCCESS, paymentService. pointList(userDetails.getUser())));
    }
}
