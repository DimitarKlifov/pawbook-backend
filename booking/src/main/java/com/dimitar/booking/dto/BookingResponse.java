package com.dimitar.booking.dto;

import com.dimitar.booking.entities.BookingStatus;

import java.time.LocalDateTime;

public class BookingResponse {
    private Long id;
    private String petName;
    private String serviceName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;

    public BookingResponse(Long id, String petName, String serviceName, LocalDateTime startTime, LocalDateTime endTime, BookingStatus status) {
        this.id = id;
        this.petName = petName;
        this.serviceName = serviceName;
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

    public String getServiceName() {
        return serviceName;
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
