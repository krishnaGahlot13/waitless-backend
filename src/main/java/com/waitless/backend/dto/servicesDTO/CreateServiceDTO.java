package com.waitless.backend.dto.servicesDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateServiceDTO {

    @NotNull(message = "Must fill Shall not be Empty")
    private String servicesName;

    @NotNull (message = "Must fill Shall not be Empty")
    @Positive(message = "Must be Positive Values Only")
    private float price;

    @NotNull (message = "Must fill Shall not be Empty")
    @Positive(message = "Must be Positive Values Only")
    private int estimatedTime;


    public CreateServiceDTO(String servicesName, float price, int estimatedTime) {
        this.servicesName = servicesName;
        this.price = price;
        this.estimatedTime = estimatedTime;

    }
    public CreateServiceDTO(){}

    public String getServicesName() {
        return servicesName;
    }

    public void setServicesName(String servicesName) {
        this.servicesName = servicesName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }


}
