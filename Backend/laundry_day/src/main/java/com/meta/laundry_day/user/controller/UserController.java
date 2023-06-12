package com.meta.laundry_day.user.controller;

import com.meta.laundry_day.common.dto.ResponseDto;
import com.meta.laundry_day.common.message.ResultCode;
import com.meta.laundry_day.user.dto.LoginRequestDto;
import com.meta.laundry_day.user.dto.SignupRequestDto;
import com.meta.laundry_day.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.meta.laundry_day.common.message.ResultCode.MEMBER_LOGIN_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.MEMBER_SIGNUP_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<ResultCode>> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.status(201)
                .body(new ResponseDto<>(MEMBER_SIGNUP_SUCCESS, null));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<ResultCode>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(MEMBER_LOGIN_SUCCESS, null));
    }
}
