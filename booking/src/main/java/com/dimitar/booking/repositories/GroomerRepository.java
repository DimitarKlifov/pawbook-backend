package com.dimitar.booking.repositories;

import com.dimitar.booking.entities.Groomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroomerRepository extends JpaRepository<Groomer, Long> {
}
