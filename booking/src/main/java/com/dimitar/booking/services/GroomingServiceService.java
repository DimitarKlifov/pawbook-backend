package com.dimitar.booking.services;

import com.dimitar.booking.dto.GroomingServiceRequest;
import com.dimitar.booking.dto.GroomingServiceResponse;
import com.dimitar.booking.dto.ServicePricingRequest;
import com.dimitar.booking.dto.ServicePricingResponse;
import com.dimitar.booking.entities.Groomer;
import com.dimitar.booking.entities.GroomingService;
import com.dimitar.booking.entities.ServicePricing;
import com.dimitar.booking.repositories.GroomerRepository;
import com.dimitar.booking.repositories.GroomingServiceRepository;
import com.dimitar.booking.repositories.ServicePricingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroomingServiceService {
    private final GroomingServiceRepository groomingServiceRepository;
    private final ServicePricingRepository servicePricingRepository;
    private final GroomerRepository groomerRepository;

    public GroomingServiceService(GroomingServiceRepository groomingServiceRepository, ServicePricingRepository servicePricingRepository, GroomerRepository groomerRepository) {
        this.groomingServiceRepository = groomingServiceRepository;
        this.servicePricingRepository = servicePricingRepository;
        this.groomerRepository = groomerRepository;
    }

    public GroomingServiceResponse createService(GroomingServiceRequest request){
        Groomer groomer = groomerRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No groomer found in the system"));

        GroomingService service = new GroomingService(request.getName(), groomer);
        GroomingService saved = groomingServiceRepository.save(service);

        return toResponse(saved);
    }

    public ServicePricingResponse addPricing(Long serviceId, ServicePricingRequest request){
        GroomingService service = groomingServiceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + serviceId));

        ServicePricing pricing = new ServicePricing(
                service,
                request.getDogSize(),
                request.getPrice(),
                request.getDurationMinutes()
        );

        ServicePricing saved = servicePricingRepository.save(pricing);

        return new ServicePricingResponse(saved.getDogSize(), saved.getPrice(), saved.getDurationMinutes());
    }

    public List<GroomingServiceResponse> getAllServices(){
        return groomingServiceRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public GroomingServiceResponse getServiceById(Long id){
        GroomingService service = groomingServiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + id));
        return toResponse(service);
    }

    public void deleteService(Long id){
        if(!groomingServiceRepository.existsById(id)){
            throw new IllegalArgumentException("Service not found: " + id);
        }

        groomingServiceRepository.deleteById(id);
    }

    private GroomingServiceResponse toResponse(GroomingService service){
        List<ServicePricingResponse> pricingResponses = servicePricingRepository
                .findByGroomingService(service).stream()
                .map(p -> new ServicePricingResponse(p.getDogSize(),p.getPrice(),p.getDurationMinutes()))
                .collect(Collectors.toList());

        return new GroomingServiceResponse(service.getId(), service.getName(),pricingResponses);
    }
}
