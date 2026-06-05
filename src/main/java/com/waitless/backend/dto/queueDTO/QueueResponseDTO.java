package com.waitless.backend.dto.queueDTO;

import com.waitless.backend.model.QueueCurrentStatus;

public class QueueResponseDTO {

    private int queueId;
    private String queueName;
    private int maxCapacity;
    private boolean active;
    private int currentToken;
    private int servingToken;
    private String queueCode;
    private QueueCurrentStatus currentStatus;
    private Integer serviceId;
    private String serviceName;

    public QueueResponseDTO() {}

    public QueueResponseDTO(int queueId, String queueName, int maxCapacity, boolean active,
                            int currentToken, int servingToken, String queueCode,
                            QueueCurrentStatus currentStatus, Integer serviceId, String serviceName) {
        this.queueId = queueId;
        this.queueName = queueName;
        this.maxCapacity = maxCapacity;
        this.active = active;
        this.currentToken = currentToken;
        this.servingToken = servingToken;
        this.queueCode = queueCode;
        this.currentStatus = currentStatus;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    public int getQueueId() { return queueId; }
    public void setQueueId(int queueId) { this.queueId = queueId; }

    public String getQueueName() { return queueName; }
    public void setQueueName(String queueName) { this.queueName = queueName; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public int getCurrentToken() { return currentToken; }
    public void setCurrentToken(int currentToken) { this.currentToken = currentToken; }

    public int getServingToken() { return servingToken; }
    public void setServingToken(int servingToken) { this.servingToken = servingToken; }

    public String getQueueCode() { return queueCode; }
    public void setQueueCode(String queueCode) { this.queueCode = queueCode; }

    public QueueCurrentStatus getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(QueueCurrentStatus currentStatus) { this.currentStatus = currentStatus; }

    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
}