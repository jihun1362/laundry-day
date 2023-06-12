package com.meta.laundry_day.user.service;

import com.meta.laundry_day.common.exception.CustomException;
import com.meta.laundry_day.security.jwt.JwtUtil;
import com.meta.laundry_day.user.dto.LoginRequestDto;
import com.meta.laundry_day.user.dto.SignupRequestDto;
import com.meta.laundry_day.user.entity.User;
import com.meta.laundry_day.user.mapper.UserMapper;
import com.meta.laundry_day.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.meta.laundry_day.common.message.ErrorCode.DUPLICATE_EMAIL_ERROE;
import static com.meta.laundry_day.common.message.ErrorCode.DUPLICATE_PHONENUMBER_ERROE;
import static com.meta.laundry_day.common.message.ErrorCode.INCORRECT_PASSWORD_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.MEMBER_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        User user = userMapper.toUser(signupRequestDto);

        // 이메일 중복 확인
        Optional<User> emailDuplicateCheck = userRepository.findByEmail(user.getEmail());
        if (emailDuplicateCheck.isPresent()) {
            throw new CustomException(DUPLICATE_EMAIL_ERROE);
        }

        // 핸드폰번호 중복 확인
        Optional<User> phoneNumberDuplicateCheck = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (phoneNumberDuplicateCheck.isPresent()) {
            throw new CustomException(DUPLICATE_PHONENUMBER_ERROE);
        }

        userRepository.save(user);
    }

    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        //멤버가 존재하는지 확인
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND_ERROR)
        );

        //비밀번호 일치 확인
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(INCORRECT_PASSWORD_ERROR);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getRole()));
    }
}
