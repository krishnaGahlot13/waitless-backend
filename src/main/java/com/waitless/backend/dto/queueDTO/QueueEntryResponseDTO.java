package com.waitless.backend.dto.queueDTO;


import com.waitless.backend.model.QueueStatus;
import jakarta.validation.constraints.NotNull;

public class QueueEntryResponseDTO {
    @NotNull(message = "Must fill Shall not be Empty")
    private String customerName;

    @NotNull (message = "Must fill Shall not be Empty")
    private String displayToken;

    @NotNull (message = "Must fill Shall not be Empty")
    private QueueStatus status;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDisplayToken() {
        return displayToken;
    }

    public void setDisplayToken(String displayToken) {
        this.displayToken = displayToken;
    }

    public QueueStatus getStatus() {
        return status;
    }

    public void setStatus(QueueStatus status) {
        this.status = status;
    }
}