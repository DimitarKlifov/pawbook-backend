package com.dimitar.booking.dto;

import com.dimitar.booking.entities.DogSize;

public class PetResponse {
    private Long id;
    private String name;
    private String breed;
    private String notes;
    private DogSize size;
    private Long ownerId;

    public PetResponse(Long id, String name, String breed, String notes, DogSize size, Long ownerId) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.notes = notes;
        this.size = size;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String getNotes() {
        return notes;
    }

    public DogSize getSize() {
        return size;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}
