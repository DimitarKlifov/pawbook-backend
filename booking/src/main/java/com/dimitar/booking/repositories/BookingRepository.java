package com.dimitar.booking.repositories;

import com.dimitar.booking.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByGroomerIdAndStartTimeBetween(Long groomerId, LocalDateTime start, LocalDateTime end);
    List<Booking> findByClientId(Long clientId);
}
