package com.dimitar.booking.dto;

import com.dimitar.booking.entities.BookingStatus;
import com.dimitar.booking.entities.DogSize;

import java.time.LocalDateTime;

public class BookingResponse {
    private Long id;
    private String petName;
    private DogSize petSize;
    private String clientName;
    private String clientPhone;
    private String serviceNameMk;
    private String serviceNameEn;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;

    public BookingResponse(Long id, String petName, DogSize petSize, String clientName, String clientPhone, String serviceNameMk, String serviceNameEn, LocalDateTime startTime, LocalDateTime endTime, BookingStatus status) {
        this.id = id;
        this.petName = petName;
        this.petSize = petSize;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.serviceNameMk = serviceNameMk;
        this.serviceNameEn = serviceNameEn;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getPetName() {
        return petName;
    }

    public DogSize getPetSize() {
        return petSize;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public String getServiceNameMk() {
        return serviceNameMk;
    }

    public String getServiceNameEn() {
        return serviceNameEn;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public BookingStatus getStatus() {
        return status;
    }
}