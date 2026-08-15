package com.dimitar.booking.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingRequest {
    @NotNull
    private Long petId;

    @NotNull
    private Long serviceId;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startTime;


    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
