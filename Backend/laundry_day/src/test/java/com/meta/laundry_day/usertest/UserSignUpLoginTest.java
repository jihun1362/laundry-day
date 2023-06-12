package com.meta.laundry_day.usertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
public class UserSignUpLoginTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void A_signupTest1() throws Exception {
        //given
        String email = "jihun1362@nate.com";
        String password = "123qwe!@#";
        String nickname = "지훈";
        String phoneNumber = "010-1234-5678";

        //when
        /**
         * Object를 JSON으로 변환
         * */
        String body = mapper.writeValueAsString(
                SignupRequestDtotest.builder()
                        .email(email)
                        .password(password)
                        .nickname(nickname)
                        .phoneNumber(phoneNumber)
                        .build()
        );

        //then
        mvc.perform(post("/api/users/signup")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입 전달값 유효성 테스트")
    void B_signupTest2() throws Exception {
        //given
        String email1 = "jihun1362@nate.com";
        String email2 = "jihun1362";
        String password1 = "123qwe!@#";
        String password2 = "123";
        String nickname1 = "지훈";
        String nickname2 = "훈";
        String phoneNumber1 = "010-1234-5678";
        String phoneNumber2 = "01012345678";

        //when
        /**
         * Object를 JSON으로 변환
         * */
        //이메일 형식 에러
        String body1 = mapper.writeValueAsString(
                SignupRequestDtotest.builder()
                        .email(email2)
                        .password(password1)
                        .nickname(nickname1)
                        .phoneNumber(phoneNumber1)
                        .build()
        );
        //비밀번호 형식 에러
        String body2 = mapper.writeValueAsString(
                SignupRequestDtotest.builder()
                        .email(email1)
                        .password(password2)
                        .nickname(nickname1)
                        .phoneNumber(phoneNumber1)
                        .build()
        );
        //닉네임 형식 에러
        String body3 = mapper.writeValueAsString(
                SignupRequestDtotest.builder()
                        .email(email1)
                        .password(password1)
                        .nickname(nickname2)
                        .phoneNumber(phoneNumber1)
                        .build()
        );
        //핸드폰번호 형식 에러
        String body4 = mapper.writeValueAsString(
                SignupRequestDtotest.builder()
                        .email(email1)
                        .password(password1)
                        .nickname(nickname1)
                        .phoneNumber(phoneNumber2)
                        .build()
        );


        //then
        //이메일 형식 에러
        mvc.perform(post("/api/users/signup")
                        .content(body1) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isBadRequest());
        //비밀번호 형식 에러
        mvc.perform(post("/api/users/signup")
                        .content(body2) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isBadRequest());
        //닉네임 형식 에러
        mvc.perform(post("/api/users/signup")
                        .content(body3) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isBadRequest());
        //핸드폰번호 형식 에러
        mvc.perform(post("/api/users/signup")
                        .content(body4) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 이메일, 핸드폰번호 중복 테스트")
    void E_signupTest3() throws Exception {
        //given
        String email1 = "jihun1362@nate.com";
        String email2 = "2jihun1362@nate.com";
        String password = "123qwe!@#";
        String nickname2 = "지훈2";
        String phoneNumber1 = "010-1234-5678";
        String phoneNumber2 = "010-1234-5679";
        //when
        //이메일 중복 체크
        String body1 = mapper.writeValueAsString(
                SignupRequestDtotest.builder()
                        .email(email1)
                        .password(password)
                        .nickname(nickname2)
                        .phoneNumber(phoneNumber2)
                        .build()
        );
        //핸드폰번호 중복 체크
        String body3 = mapper.writeValueAsString(
                SignupRequestDtotest.builder()
                        .email(email2)
                        .password(password)
                        .nickname(nickname2)
                        .phoneNumber(phoneNumber1)
                        .build()
        );

        //then
        //이메일 중복 체크
        mvc.perform(post("/api/users/signup")
                        .content(body1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        //핸드폰번호 중복 체크
        mvc.perform(post("/api/users/signup")
                        .content(body3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 이메일 일치 실패 테스트")
    void F_loginTest1() throws Exception {
        //given
        String email1 = "1jihun1362@nate.com";
        String password1 = "123qwe!@#";

        //when
        String body1 = mapper.writeValueAsString(
                LoginRequestDtoTest.builder()
                        .email(email1)
                        .password(password1)
                        .build()
        );

        //then
        mvc.perform(post("/api/users/login")
                        .content(body1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인 비밀번호 실패 테스트")
    void G_loginTest2() throws Exception {
        //given
        String email1 = "jihun1362@nate.com";
        String password1 = "123qwe!@#123";

        //when
        String body1 = mapper.writeValueAsString(
                LoginRequestDtoTest.builder()
                        .email(email1)
                        .password(password1)
                        .build()
        );

        //then
        mvc.perform(post("/api/users/login")
                        .content(body1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void H_loginTest3() throws Exception {
        //given
        String email1 = "jihun1362@nate.com";
        String password1 = "123qwe!@#";


        //when
        String body1 = mapper.writeValueAsString(
                LoginRequestDtoTest.builder()
                        .email(email1)
                        .password(password1)
                        .build()
        );

        //then
        String token = mvc.perform(post("/api/users/login")
                        .content(body1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("로그인되었습니다."))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andReturn().getResponse().getHeader("Authorization");
    }
}
