package com.dimitar.booking.services;

import com.dimitar.booking.dto.AvailabilityResponse;
import com.dimitar.booking.dto.BookingRequest;
import com.dimitar.booking.dto.BookingResponse;
import com.dimitar.booking.entities.*;
import com.dimitar.booking.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final GroomerRepository groomerRepository;
    private final PetRepository petRepository;
    private final GroomingServiceRepository groomingServiceRepository;
    private final ServicePricingRepository servicePricingRepository;
    private final WorkDayRepository workDayRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, GroomerRepository groomerRepository, PetRepository petRepository, GroomingServiceRepository groomingServiceRepository, ServicePricingRepository servicePricingRepository, WorkDayRepository workDayRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.groomerRepository = groomerRepository;
        this.petRepository = petRepository;
        this.groomingServiceRepository = groomingServiceRepository;
        this.servicePricingRepository = servicePricingRepository;
        this.workDayRepository = workDayRepository;
        this.userRepository = userRepository;
    }

    public AvailabilityResponse getAvailability(LocalDate date, Long serviceId, Long petId){
        WorkDay workDay = workDayRepository.findByDate(date).orElse(null);

        if (workDay == null || !workDay.isOpen()){
            return new AvailabilityResponse(false, List.of());
        }


        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found: " + petId));

        GroomingService service = groomingServiceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + serviceId));

        ServicePricing pricing = servicePricingRepository.findByGroomingServiceAndDogSize(service, pet.getSize())
                .orElseThrow(() -> new IllegalArgumentException("No pricing set for this service and pet size"));


        int durationMinutes = pricing.getDurationMinutes();

        Groomer groomer = groomerRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No groomer found in the system"));

        LocalDateTime dayStart = date.atTime(workDay.getStartTime());
        LocalDateTime dayEnd = date.atTime(workDay.getEndTime());

        List<Booking> existingBookings = bookingRepository.findByGroomerIdAndStartTimeBetween(groomer.getId(), dayStart, dayEnd)
                .stream()
                .filter(b -> b.getStatus() != BookingStatus.CANCELLED)
                .collect(Collectors.toList());

        List<LocalTime> availableSlots = new ArrayList<>();
        LocalDateTime slotStart = dayStart;

        while (!slotStart.plusMinutes(durationMinutes).isAfter(dayEnd)){
            LocalDateTime slotEnd = slotStart.plusMinutes(durationMinutes);
            final LocalDateTime currentSlotStart = slotStart;

            boolean overlaps = existingBookings.stream().anyMatch(b ->
                    currentSlotStart.isBefore(b.getEndTime()) && b.getStartTime().isBefore(slotEnd));

            if (!overlaps){
                availableSlots.add(currentSlotStart.toLocalTime());
            }

            slotStart = slotStart.plusMinutes(durationMinutes);
        }

        return  new AvailabilityResponse(true,availableSlots);
    }

    public BookingResponse createBooking(BookingRequest request){
        WorkDay workDay = workDayRepository.findByDate(request.getDate())
                .orElseThrow(() -> new IllegalArgumentException("No WorkDay found for date: " + request.getDate()));

        if (!workDay.isOpen()) {
            throw new IllegalArgumentException("Groomer is closed on this date: " + request.getDate());
        }

        User client = userRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + request.getClientId()));

        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("Pet not found: " + request.getPetId()));

        GroomingService service = groomingServiceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + request.getServiceId()));

        ServicePricing pricing = servicePricingRepository.findByGroomingServiceAndDogSize(service, pet.getSize())
                .orElseThrow(() -> new IllegalArgumentException("No pricing set for this service and pet size"));

        Groomer groomer = groomerRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No groomer found in the system"));

        LocalDateTime requestedStart = request.getDate().atTime(request.getStartTime());
        LocalDateTime requestedEnd = requestedStart.plusMinutes(pricing.getDurationMinutes());

        LocalDateTime dayStart = request.getDate().atTime(workDay.getStartTime());
        LocalDateTime dayEnd = request.getDate().atTime(workDay.getEndTime());

        if (requestedStart.isBefore(dayStart) || requestedEnd.isAfter(dayEnd)){
            throw new IllegalArgumentException("Requested time is outside working hours");
        }

        List<Booking> existingBookings = bookingRepository.findByGroomerIdAndStartTimeBetween(groomer.getId(), dayStart, dayEnd)
                .stream()
                .filter(b -> b.getStatus() != BookingStatus.CANCELLED)
                .collect(Collectors.toList());
        boolean overlaps = existingBookings.stream().anyMatch(b ->
                requestedStart.isBefore(b.getEndTime()) && b.getStartTime().isBefore(requestedEnd));

        if (overlaps){
            throw new IllegalArgumentException("This time slot is already booked");
        }

        Booking booking = new Booking(client,groomer,service,pet,requestedStart,requestedEnd,BookingStatus.CONFIRMED);
        Booking saved = bookingRepository.save(booking);

        return toResponse(saved);
    }

    public List<BookingResponse> getBookingsByClient(Long clientId){
        return bookingRepository.findByClientId(clientId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getAllBookings(){
        return bookingRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    public BookingResponse cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + id));

        booking.setStatus(BookingStatus.CANCELLED);
        Booking saved = bookingRepository.save(booking);

        return toResponse(saved);
    }


    private BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getPet().getName(),
                booking.getService().getName(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getStatus()
        );
    }
}
