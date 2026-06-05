package com.waitless.backend.dto.queueDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class QueueEntryDTO {
    @NotNull(message = "Must fill Shall not be Empty")
    private String customerName;
    @Pattern(regexp = "^[0-9]{10}$")
    private String mobileNumber;
    @Positive(message = "Queue ID must be positive")
    private int servicesId;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getServicesId() {
        return servicesId;
    }

    public void setServicesId(int servicesId) {
        this.servicesId = servicesId;
    }
}
