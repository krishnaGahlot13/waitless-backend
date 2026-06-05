package com.waitless.backend.model;

import jakarta.persistence.*;

@Entity

        public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
                private int  userId;
                private String mobileNumber;
                private String email;
                @Enumerated(EnumType.STRING)
                private Roles role;
                private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }


}

