package com.dimitar.booking.dto;

import jakarta.validation.constraints.NotBlank;

public class GroomingServiceRequest {
    @NotBlank
    private String nameMk;

    @NotBlank
    private String nameEn;

    public String getNameMk() {
        return nameMk;
    }

    public void setNameMk(String nameMk) {
        this.nameMk = nameMk;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}