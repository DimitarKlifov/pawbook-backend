package com.dimitar.booking.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class GroomingService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int durationMinutes;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "groomer_id")
    private Groomer groomer;


    public GroomingService() {
    }

    public GroomingService(String name, int durationMinutes, BigDecimal price, Groomer groomer) {
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.price = price;
        this.groomer = groomer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Groomer getGroomer() {
        return groomer;
    }

    public void setGroomer(Groomer groomer) {
        this.groomer = groomer;
    }
}
