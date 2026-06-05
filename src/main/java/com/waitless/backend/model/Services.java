package com.waitless.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int servicesId;
    private String servicesName;
    private float price;
    private int estimatedTime;
    @ManyToOne
    @JoinColumn(name = "businessId")
    private Businesses businesses;
    @OneToOne
    @JoinColumn(name = "queueId")
    private Queue queue;

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Businesses getBusinesses() {
        return businesses;
    }

    public void setBusinesses(Businesses businesses) {
        this.businesses = businesses;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getServicesName() {
        return servicesName;
    }

    public void setServicesName(String servicesName) {
        this.servicesName = servicesName;
    }

    public int getServicesId() {
        return servicesId;
    }

    public void setServicesId(int servicesId) {
        this.servicesId = servicesId;
    }




}
