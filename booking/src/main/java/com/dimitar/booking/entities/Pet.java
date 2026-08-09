package com.dimitar.booking.entities;

import jakarta.persistence.*;


@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String breed;

    private String notes;

    @Enumerated(EnumType.STRING)
    private DogSize size;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Pet() {
    }

    public Pet(String name, String breed, String notes, DogSize size, User owner) {
        this.name = name;
        this.breed = breed;
        this.notes = notes;
        this.size = size;
        this.owner = owner;
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public DogSize getSize() {
        return size;
    }

    public void setSize(DogSize size) {
        this.size = size;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}