package com.meta.laundry_day.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {

    SUCCESS("성공", 200),

    //회원가입, 로그인 관련
    MEMBER_SIGNUP_SUCCESS("회원가입에 성공하였습니다", 201),
    MEMBER_LOGIN_SUCCESS("로그인되었습니다.", 200),

    //주소
    ADDRESS_CREATE_SUCCESS("주소가 등록되었습니다.", 201),
    ADDRESS_REQUEST_SUCCESS("주소를 조회하였습니다.", 200),

    //이벤트
    EVENT_CREATE_SUCCESS("이벤트가 등록되었습니다.", 201),
    EVENT_LIST_REQUEST_SUCCESS("이벤트 목록을 조회하였습니다.", 200),
    EVENT_DETAIL_REQUEST_SUCCESS("이벤트를 조회하였습니다.", 200),
    EVENT_MODIFY_SUCCESS("이벤트를 수정하였습니다.", 200),

    //안정가
    WASHINFTYPE_CREATE_SUCCESS("세탁타입이 등록되었습니다.", 201),
    WASHINFTYPE_MODIFY_SUCCESS("세탁타입이 수정되었습니다.", 200),
    WASHINFTYPE_DELETE_SUCCESS("세탁타입이 삭제되었습니다.", 200),
    STABLEPRICING_LIST_REQUEST_SUCCESS("안정가 목록을 조회하였습니다.", 200),
    STABLEPRICING_CREATE_SUCCESS("안정가를 등록하었습니다.", 201),
    STABLEPRICING_MODIFY_SUCCESS("안정가를 수정하였습니다.", 200),
    STABLEPRICING_DELETE_SUCCESS("안정가를 삭제하였습니다.", 200),

    //카드
    CARD_CREATE_SUCCESS("카드를 등록하였습니다.", 201),
    CARD_LIST_REQUEST_SUCCESS("카드 목록을 조회하였습니다.", 200),
    REP_CARD_DESIGNATE_SUCCESS("대표카드를 카드를 지정하였습니다.", 200),
    CARD_DELETE_SUCCESS("카드를 삭제하였습니다.", 200),

    //주문
    ORDER_CREATE_SUCCESS("세탁 주문을 요청하였습니다.", 201),
    ORDER_REQUEST_SUCCESS("세탁 주문 조회하였습니다.", 200),
    LAUNDRY_CREATE_SUCCESS("세탁물을 등록하였습니다.", 201),
    LAUNDRY_UPDATE_SUCCESS("세탁물 세탁 상태를 변경하였습니다.", 200),
    LAUNDRY_REGIST_SUCCESS("세탁물 등록을 완료하였습니다.", 200),
    ORDER_PROGRESS_CHECK_SUCCESS("주문 진행 상태를 조회하였습니다.", 200),
    ORDER_PROGRESS_UPDATE_SUCCESS("주문 진행 상태를 변경하였습니다.", 200),
    ORDER_DELETE_SUCCESS("세탁 주문을 취소하였습니다.", 200),

    //결제
    PAYMENT_CREATE_SUCCESS("결제를 완료하였습니다.", 201),
    PAYMENT_LIST_REQUEST_SUCCESS("결제 내역을 조회하였습니다.", 200),
    POINT_LIST_REQUEST_SUCCESS("포인트 내역을 조회하였습니다.", 200),

    //알람
    ALARM_LIST_REQUEST_SUCCESS("알람 내역을 조회하였습니다.", 200),
    ALARM_CHECK_SUCCESS("알람을 확인하였습니다.", 200),

    ;
    private final String msg;
    private final int statusCode;
}
