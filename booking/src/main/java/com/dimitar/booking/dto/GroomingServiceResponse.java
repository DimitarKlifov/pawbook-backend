package com.dimitar.booking.dto;

import java.util.List;

public class GroomingServiceResponse {

    private Long id;
    private String name;
    private List<ServicePricingResponse> pricing;

    public GroomingServiceResponse(Long id, String name, List<ServicePricingResponse> pricing) {
        this.id = id;
        this.name = name;
        this.pricing = pricing;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ServicePricingResponse> getPricing() {
        return pricing;
    }
}
