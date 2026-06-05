package com.waitless.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int queueId;

    private String queueName;

    private int maxCapacity;

    @Enumerated(EnumType.STRING)
    private QueueCurrentStatus currentStatus;

    private int token;

    private int currentToken;

    private int servingToken;

    private boolean active;

    private String queueCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceId")
    private Services services;

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
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

    public QueueCurrentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(QueueCurrentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(int currentToken) {
        this.currentToken = currentToken;
    }

    public int getServingToken() {
        return servingToken;
    }

    public void setServingToken(int servingToken) {
        this.servingToken = servingToken;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getQueueCode() {
        return queueCode;
    }

    public void setQueueCode(String queueCode) {
        this.queueCode = queueCode;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }
}