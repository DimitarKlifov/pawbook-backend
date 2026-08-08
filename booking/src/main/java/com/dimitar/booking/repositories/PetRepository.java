package com.dimitar.booking.repositories;

import com.dimitar.booking.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
