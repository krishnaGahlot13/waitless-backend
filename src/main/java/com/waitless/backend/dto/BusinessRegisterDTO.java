package com.waitless.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;

public class BusinessRegisterDTO {

    @Pattern(regexp = "^[0-9]{10}$")
    private String mobileNumber;

    @Email
    private String email;

    @NotBlank
    private String password;
    private String businessName;
    private String address;
    private LocalTime openingTime;
    private LocalTime closingTime;


    // FIX: Jackson requires a no-arg constructor for request body deserialization
    public BusinessRegisterDTO() {}

    public BusinessRegisterDTO(String mobileNumber, String email, String password, String businessName, String address, LocalTime openingTime, LocalTime closingTime) {
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        this.businessName = businessName;
        this.address = address;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}