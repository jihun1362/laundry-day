package com.meta.laundry_day.user.mapper;

import com.meta.laundry_day.user.dto.SignupRequestDto;
import com.meta.laundry_day.user.entity.User;
import com.meta.laundry_day.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    /**
     * - Dto → Entity, Entity → Dto 변환의 책임을 Mapper 클래스가 가진다.
     *     1. 가시성 업 ↑
     *     2. 관리를 Mapper 에서만 하면 되니 유지보수성 업 ↑
     *     3. DTO와 Entity간의 결합도 줄이기!
     */
    private final PasswordEncoder passwordEncoder;
    public User toUser(SignupRequestDto signupRequestDto){
        return User.builder()
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .nickname(signupRequestDto.getNickname())
                .phoneNumber(signupRequestDto.getPhoneNumber())
                .role(UserRoleEnum.USER)
                .point(0.0)
                .build();
    }
}
