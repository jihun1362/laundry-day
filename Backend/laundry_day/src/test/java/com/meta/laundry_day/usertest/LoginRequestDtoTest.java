package com.meta.laundry_day.usertest;

public class LoginRequestDtoTest {
    private String email;
    private String password;

    LoginRequestDtoTest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginRequestDtoTestBuilder builder() {
        return new LoginRequestDtoTestBuilder();
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public static class LoginRequestDtoTestBuilder {
        private String email;
        private String password;

        LoginRequestDtoTestBuilder() {
        }

        public LoginRequestDtoTestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public LoginRequestDtoTestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public LoginRequestDtoTest build() {
            return new LoginRequestDtoTest(email, password);
        }

        public String toString() {
            return "LoginRequestDtoTest.LoginRequestDtoTestBuilder(email=" + this.email + ", password=" + this.password + ")";
        }
    }
}
