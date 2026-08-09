package com.dimitar.booking.dto;

import com.dimitar.booking.entities.DogSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PetRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String breed;

    private String notes;

    @NotNull
    private DogSize size;

    @NotNull
    private Long ownerId;

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
