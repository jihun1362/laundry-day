package com.meta.laundry_day.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //회원가입, 로그인 에러
    INVALID_EMAIL_PATTERN_ERROR("email 형식에 맞지 않습니다.", 400),
    INVALID_PASSWORD_PATTERN_ERROR("비밀번호는 영문, 숫자, 특수문자(!@#$%^&+=)는 한 문자씩 포함 최소 8자리 이상입니다.", 400),
    INVALID_NICKNAME_PATTERN("닉네임은 영문(소문자), 숫자, 한글(자음, 모음 단위 x) 조합에 2~10자입니다.", 400),
    INVALID_PHONENUMBER_PATTERN("핸드폰번호의 형식은 '***-****-****' 이며 '-'를 생략하지 않습니다.", 400),
    DUPLICATE_EMAIL_ERROE("중복된 이메일이 존재합니다.", 400),
    DUPLICATE_PHONENUMBER_ERROE("중복된 핸드폰번호가 존재합니다.", 400),
    MEMBER_NOT_FOUND_ERROR("존재하지 않는 이메일이거나 이메일이 올바르지 않습니다.", 404),
    INCORRECT_PASSWORD_ERROR("비밀번호가 일치하지 않습니다.", 404),

    //카드
    REP_CARD_DESIGNATE_ERROR("대표카드 지정은 한 카드만 가능합니다.", 400),
    CARD_INFORM_NOT_FOUNT_ERROR("카드 정보를 찾을 수 없습니다.", 400),

    //주문
    ORDER_ONLY_ONE_ERROR("중복으로 세탁 주문은 불가능합니다.", 400),
    LAUNDRY_RESIST_DONE_ERROR("세탁물 등록이 완료되어 더 등록 할수 없습니다.", 400),
    LAUNDRY_PICKUP_START_ERROR("세탁물 수거가 시작되어 주문을 취소할 수 없습니다.", 400),
    LAUNDRY_PICKUP_NOT_DONE_ERROR("세탁물 수거가 완료되지 않았습니다.", 400),
    LAUNDRY_REGIST_DONE_ERROR("세탁물 등록이 이미 완료되었습니다.", 400),

    //404 찾을 수 없음
    ADDRESS_NOT_FOUND("해당 주소를 찾을 수가 없습니다.", 404),
    EVENT_NOT_FOUND("해당 이벤트를 찾을 수가 없습니다.", 404),
    STABLEPRICING_NOT_FOUND("해당 안정가를 찾을 수가 없습니다.", 404),
    CARD_NOT_FOUND("해당 카드를 찾을 수가 없습니다.", 404),
    ORDER_NOT_FOUND("해당 주문을 찾을 수가 없습니다.", 404),
    PROGRESS_NOT_FOUND("해당 진행 상태을 찾을 수가 없습니다.", 404),
    LAUNDRY_NOT_FOUND("해당 세탁물을 찾을 수가 없습니다.", 404),
    ALARM_NOT_FOUND("해당 알람을 찾을 수가 없습니다.", 404),

    //401 잘못된 권한 접근
    AUTHORIZATION_DELETE_FAIL("삭제 권한이 없습니다.", 401),
    AUTHORIZATION_UPDATE_FAIL("수정 권한이 없습니다.", 401),
    AUTHORIZATION_FAIL("권한이 없습니다.", 401),

    //필터부분 에러
    FORBIDDEN_ERROR("서버 사용 권한이 없습니다.", 403),
    TOKEN_ERROR("토큰이 유효하지 않습니다.", 401),
    USER_NOT_FOUND_ERROR("존재하지 않는 유저 입니다.", 404),

    //서버 에러
    INTERNAL_SERVER_ERROR("서버 에러입니다. 서버 팀에 연락주세요!", 500),
    USER_AUTHORIZATION_FAIL_ERROR("사용자가 해당 API에 접근 권한이 없습니다.", 401),

    ;
    private final String msg;
    private final int statusCode;
}
