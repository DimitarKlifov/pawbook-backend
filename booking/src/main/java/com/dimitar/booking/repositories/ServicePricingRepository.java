package com.dimitar.booking.repositories;

import com.dimitar.booking.entities.DogSize;
import com.dimitar.booking.entities.GroomingService;
import com.dimitar.booking.entities.ServicePricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServicePricingRepository extends JpaRepository<ServicePricing, Long> {
    List<ServicePricing> findByGroomingService (GroomingService groomingService);
    Optional<ServicePricing> findByGroomingServiceAndDogSize(GroomingService groomingService, DogSize dogSize);
}
