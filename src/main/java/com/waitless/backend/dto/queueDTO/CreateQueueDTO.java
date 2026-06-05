package com.waitless.backend.dto.queueDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CreateQueueDTO {
    @NotBlank(message = "Queue name is required")
    private String queueName;
    @Positive(message = " ID must be positive")
    private int serviceId;

    @Min(value = 0)
    private int maxCapacity;

    @NotBlank (message = "Queue code is required")
    private String queueCode;

    public CreateQueueDTO(String queueName, int maxCapacity) {
        this.queueName = queueName;
        this.maxCapacity = maxCapacity;
    }

    public CreateQueueDTO() {
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getQueueCode() {
        return queueCode;
    }

    public void setQueueCode(String queueCode) {
        this.queueCode = queueCode;
    }
}
