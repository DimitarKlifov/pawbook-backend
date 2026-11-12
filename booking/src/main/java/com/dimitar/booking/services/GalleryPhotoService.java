package com.dimitar.booking.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dimitar.booking.dto.GalleryPhotoResponse;
import com.dimitar.booking.entities.GalleryPhoto;
import com.dimitar.booking.repositories.GalleryPhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GalleryPhotoService {
    private final GalleryPhotoRepository galleryPhotoRepository;
    private final Cloudinary cloudinary;

    public GalleryPhotoService(GalleryPhotoRepository galleryPhotoRepository, Cloudinary cloudinary) {
        this.galleryPhotoRepository = galleryPhotoRepository;
        this.cloudinary = cloudinary;
    }

    public GalleryPhotoResponse uploadPhoto(MultipartFile file, String breed){
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");

            GalleryPhoto photo = new GalleryPhoto(imageUrl,breed);
            GalleryPhoto saved = galleryPhotoRepository.save(photo);

            return toResponse(saved);
        } catch (IOException e){
            throw new IllegalArgumentException("Failed to upload image: " + e.getMessage());
        }
    }

    public List<GalleryPhotoResponse> getPhotoByBreed(String breed){
        List<GalleryPhoto> photos;
        if (breed == null || breed.isBlank()){
            photos = galleryPhotoRepository.findAll();
        }else {
            photos = galleryPhotoRepository.findByBreed(breed);
        }

        return photos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deletePhoto(Long id){
        if (!galleryPhotoRepository.existsById(id)){
            throw new IllegalArgumentException("Photo not found: " + id);
        }
        galleryPhotoRepository.deleteById(id);
    }

    public GalleryPhotoResponse updateBreed(Long id, String breed) {
        GalleryPhoto photo = galleryPhotoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Photo not found: " + id));

        photo.setBreed(breed);
        GalleryPhoto saved = galleryPhotoRepository.save(photo);

        return toResponse(saved);
    }

    private GalleryPhotoResponse toResponse(GalleryPhoto photo){
        return new GalleryPhotoResponse(photo.getId(), photo.getImageUrl(), photo.getBreed());
    }
}
