package com.waitless.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegisterDTO {
        @Pattern(regexp = "^[0-9]{10}$")
        private String mobileNumber;
        @Email
        private String email;
        @NotBlank
        private String password;

        public RegisterDTO(String mobileNumber, String email, String password) {
                this.mobileNumber = mobileNumber;
                this.email = email;
                this.password = password;
        }
        public RegisterDTO() {}

        public String getMobileNumber() {
                return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}
