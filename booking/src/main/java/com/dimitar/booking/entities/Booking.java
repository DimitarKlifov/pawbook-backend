package com.dimitar.booking.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "groomer_id")
    private Groomer groomer;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private GroomingService service;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public Booking() {
    }

    public Booking(User client, Groomer groomer, GroomingService service, Pet pet, LocalDateTime startTime, LocalDateTime endTime, BookingStatus status) {
        this.client = client;
        this.groomer = groomer;
        this.service = service;
        this.pet = pet;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Groomer getGroomer() {
        return groomer;
    }

    public void setGroomer(Groomer groomer) {
        this.groomer = groomer;
    }

    public GroomingService getService() {
        return service;
    }

    public void setService(GroomingService service) {
        this.service = service;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
