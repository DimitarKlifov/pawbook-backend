package com.dimitar.booking.dto;

public class GalleryPhotoResponse {
    private Long id;
    private String imageUrl;
    private String breed;

    public GalleryPhotoResponse(Long id, String imageUrl, String breed) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.breed = breed;
    }

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBreed() {
        return breed;
    }
}
