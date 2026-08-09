package com.dimitar.booking.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class WorkDayResponse {
    private Long id;
    private LocalDate date;
    private boolean open;
    private LocalTime startTime;
    private LocalTime endTime;

    public WorkDayResponse(Long id, LocalDate date, boolean open, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.date = date;
        this.open = open;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isOpen() {
        return open;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
