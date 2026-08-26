package com.dimitar.booking.controllers;

import com.dimitar.booking.dto.GalleryPhotoResponse;
import com.dimitar.booking.services.GalleryPhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/gallery")
public class GalleryPhotoController {

    private final GalleryPhotoService galleryPhotoService;

    public GalleryPhotoController(GalleryPhotoService galleryPhotoService) {
        this.galleryPhotoService = galleryPhotoService;
    }

    @PostMapping
    public ResponseEntity<GalleryPhotoResponse> uploadPhoto(@RequestParam("file")MultipartFile file, @RequestParam("breed") String breed){
        GalleryPhotoResponse response = galleryPhotoService.uploadPhoto(file, breed);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GalleryPhotoResponse>> getPhotos(@RequestParam(required = false) String breed){
        return ResponseEntity.ok(galleryPhotoService.getPhotoByBreed(breed));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id){
        galleryPhotoService.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }
}
