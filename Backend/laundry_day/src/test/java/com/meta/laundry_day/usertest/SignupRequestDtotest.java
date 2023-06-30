package com.meta.laundry_day.usertest;

public class SignupRequestDtotest {
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

    SignupRequestDtotest(String email, String password, String nickname, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public static SignupRequestDtotestBuilder builder() {
        return new SignupRequestDtotestBuilder();
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public static class SignupRequestDtotestBuilder {
        private String email;
        private String password;
        private String nickname;
        private String phoneNumber;

        SignupRequestDtotestBuilder() {
        }

        public SignupRequestDtotestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public SignupRequestDtotestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public SignupRequestDtotestBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public SignupRequestDtotestBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public SignupRequestDtotest build() {
            return new SignupRequestDtotest(email, password, nickname, phoneNumber);
        }

        public String toString() {
            return "SignupRequestDtotest.SignupRequestDtotestBuilder(email=" + this.email + ", password=" + this.password + ", nickname=" + this.nickname + ", phoneNumber=" + this.phoneNumber + ")";
        }
    }
}
