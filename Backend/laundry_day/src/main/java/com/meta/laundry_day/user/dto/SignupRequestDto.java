package com.meta.laundry_day.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9_\\\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&+=])[A-Za-z\\d!@#$%^&+=]{8,}$")
    private String password;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z0*9가-힣])[a-z0-9가-힣]{2,10}$")
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    private String phoneNumber;
}
