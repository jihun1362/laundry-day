package com.meta.laundry_day.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {

    SUCCESS("성공",200),

    //회원가입, 로그인 관련
    MEMBER_SIGNUP_SUCCESS("회원가입에 성공하였습니다",201),
    EMAIL_CHECK_SUCCESS("사용가능한 이메일입니다.",200),
    MEMBER_LOGIN_SUCCESS("로그인되었습니다.",200),




    ;
    private final String msg;
    private final int statusCode;
}
