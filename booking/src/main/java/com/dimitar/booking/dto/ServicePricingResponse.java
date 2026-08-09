package com.dimitar.booking.dto;

import com.dimitar.booking.entities.DogSize;

import java.math.BigDecimal;

public class ServicePricingResponse {
    private DogSize dogSize;
    private BigDecimal price;
    private int durationMinutes;

    public ServicePricingResponse(DogSize dogSize, BigDecimal price, int durationMinutes) {
        this.dogSize = dogSize;
        this.price = price;
        this.durationMinutes = durationMinutes;
    }

    public DogSize getDogSize() {
        return dogSize;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}
