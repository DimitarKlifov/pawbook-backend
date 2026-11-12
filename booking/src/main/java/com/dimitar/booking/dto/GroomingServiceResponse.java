package com.dimitar.booking.dto;

import java.util.List;

public class GroomingServiceResponse {

    private Long id;
    private String nameMk;
    private String nameEn;
    private List<ServicePricingResponse> pricing;

    public GroomingServiceResponse(Long id, String nameMk, String nameEn, List<ServicePricingResponse> pricing) {
        this.id = id;
        this.nameMk = nameMk;
        this.nameEn = nameEn;
        this.pricing = pricing;
    }

    public Long getId() {
        return id;
    }

    public String getNameMk() {
        return nameMk;
    }

    public String getNameEn() {
        return nameEn;
    }

    public List<ServicePricingResponse> getPricing() {
        return pricing;
    }
}