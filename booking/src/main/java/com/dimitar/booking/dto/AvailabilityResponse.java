package com.dimitar.booking.dto;

import java.time.LocalTime;
import java.util.List;

public class AvailabilityResponse {
    private boolean dayOpen;
    private List<LocalTime> availableSlots;

    public AvailabilityResponse(boolean dayOpen, List<LocalTime> availableSlots) {
        this.dayOpen = dayOpen;
        this.availableSlots = availableSlots;
    }

    public boolean isDayOpen() {
        return dayOpen;
    }

    public List<LocalTime> getAvailableSlots() {
        return availableSlots;
    }
}
