package com.dimitar.booking.repositories;

import com.dimitar.booking.entities.GalleryPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryPhotoRepository extends JpaRepository<GalleryPhoto,Long> {
    List<GalleryPhoto> findByBreed(String breed);
}
