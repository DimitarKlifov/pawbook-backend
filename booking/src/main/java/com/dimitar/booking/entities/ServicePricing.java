package com.dimitar.booking.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ServicePricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grooming_service_id")
    private GroomingService groomingService;

    @Enumerated(EnumType.STRING)
    private DogSize dogSize;

    private BigDecimal price;

    private int durationMinutes;

    public ServicePricing() {
    }

    public ServicePricing(GroomingService groomingService, DogSize dogSize, BigDecimal price, int durationMinutes) {
        this.groomingService = groomingService;
        this.dogSize = dogSize;
        this.price = price;
        this.durationMinutes = durationMinutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroomingService getGroomingService() {
        return groomingService;
    }

    public void setGroomingService(GroomingService groomingService) {
        this.groomingService = groomingService;
    }

    public DogSize getDogSize() {
        return dogSize;
    }

    public void setDogSize(DogSize dogSize) {
        this.dogSize = dogSize;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}