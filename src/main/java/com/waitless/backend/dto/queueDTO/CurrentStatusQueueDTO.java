package com.waitless.backend.dto.queueDTO;

public class CurrentStatusQueueDTO {

    private String displayToken;

    private int waitingCount;

    private int estimatedWait;

    public String getDisplayToken() {
        return displayToken;
    }

    public void setDisplayToken(String displayToken) {
        this.displayToken = displayToken;
    }

    public int getWaitingCount() {
        return waitingCount;
    }

    public void setWaitingCount(int waitingCount) {
        this.waitingCount = waitingCount;
    }

    public int getEstimatedWait() {
        return estimatedWait;
    }

    public void setEstimatedWait(int estimatedWait) {
        this.estimatedWait = estimatedWait;
    }
}
