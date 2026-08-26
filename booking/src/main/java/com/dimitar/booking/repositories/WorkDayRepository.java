package com.dimitar.booking.repositories;

import com.dimitar.booking.entities.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
    Optional<WorkDay> findByDate(LocalDate date);
    List<WorkDay> findByDateBetween(LocalDate start, LocalDate end);
}
