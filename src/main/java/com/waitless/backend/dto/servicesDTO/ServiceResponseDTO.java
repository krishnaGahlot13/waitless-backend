package com.waitless.backend.dto.servicesDTO;

public class ServiceResponseDTO {

    private int serviceId;
    private String servicesName;
    private float price;
    private int estimatedTime;
    private int businessId;
    private String businessName;

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public String getServicesName() { return servicesName; }
    public void setServicesName(String servicesName) { this.servicesName = servicesName; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public int getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(int estimatedTime) { this.estimatedTime = estimatedTime; }

    public int getBusinessId() { return businessId; }
    public void setBusinessId(int businessId) { this.businessId = businessId; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }
}