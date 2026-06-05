package com.waitless.backend.dto;

public class UserProfileDTO {
    private String Email;
    private String mobileNUmber;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNUmber() {
        return mobileNUmber;
    }

    public void setMobileNUmber(String mobileNUmber) {
        this.mobileNUmber = mobileNUmber;
    }


}
