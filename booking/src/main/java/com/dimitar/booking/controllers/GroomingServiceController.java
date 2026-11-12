package com.dimitar.booking.controllers;


import com.dimitar.booking.dto.GroomingServiceRequest;
import com.dimitar.booking.dto.GroomingServiceResponse;
import com.dimitar.booking.dto.ServicePricingRequest;
import com.dimitar.booking.dto.ServicePricingResponse;
import com.dimitar.booking.services.GroomingServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class GroomingServiceController {
    private final GroomingServiceService groomingServiceService;

    public GroomingServiceController(GroomingServiceService groomingServiceService) {
        this.groomingServiceService = groomingServiceService;
    }

    @PostMapping
    public ResponseEntity<GroomingServiceResponse> createService(@Valid @RequestBody GroomingServiceRequest request){
        GroomingServiceResponse response = groomingServiceService.createService(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{serviceId}/pricing")
    public ResponseEntity<ServicePricingResponse> addPricing(@PathVariable Long serviceId, @Valid @RequestBody ServicePricingRequest request){
        ServicePricingResponse response = groomingServiceService.addPricing(serviceId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<GroomingServiceResponse>> getAllServices(){
        return ResponseEntity.ok(groomingServiceService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroomingServiceResponse> getServiceById(@PathVariable Long id){
        return ResponseEntity.ok(groomingServiceService.getServiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroomingServiceResponse> updateService(@PathVariable Long id, @Valid @RequestBody GroomingServiceRequest request) {
        GroomingServiceResponse response = groomingServiceService.updateService(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id){
        groomingServiceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
