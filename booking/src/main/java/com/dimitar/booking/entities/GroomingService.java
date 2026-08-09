package com.dimitar.booking.entities;

import jakarta.persistence.*;

@Entity
public class GroomingService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "groomer_id")
    private Groomer groomer;


    public GroomingService() {
    }

    public GroomingService(String name, Groomer groomer) {
        this.name = name;
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

    public Groomer getGroomer() {
        return groomer;
    }

    public void setGroomer(Groomer groomer) {
        this.groomer = groomer;
    }
}