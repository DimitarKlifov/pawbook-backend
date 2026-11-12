package com.dimitar.booking.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dimitar.booking.dto.SiteImageResponse;
import com.dimitar.booking.entities.SiteImage;
import com.dimitar.booking.entities.SiteImageSlot;
import com.dimitar.booking.repositories.SiteImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SiteImageService {
    private final SiteImageRepository siteImageRepository;
    private final Cloudinary cloudinary;

    public SiteImageService(SiteImageRepository siteImageRepository, Cloudinary cloudinary) {
        this.siteImageRepository = siteImageRepository;
        this.cloudinary = cloudinary;
    }

    public SiteImageResponse uploadImage(SiteImageSlot slot, MultipartFile file) {
        try {
            // delete the old cloudinary image first, if one exists for this slot
            siteImageRepository.findById(slot).ifPresent(existing -> {
                try {
                    cloudinary.uploader().destroy(existing.getPublicId(), ObjectUtils.emptyMap());
                } catch (IOException ignored) {
                    // old image cleanup failing shouldn't block the new upload
                }
            });

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");
            String publicId = (String) uploadResult.get("public_id");

            SiteImage siteImage = new SiteImage(slot, imageUrl, publicId);
            SiteImage saved = siteImageRepository.save(siteImage); // save() upserts since slot is the @Id

            return toResponse(saved);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to upload image: " + e.getMessage());
        }
    }

    public List<SiteImageResponse> getAllImages() {
        return siteImageRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SiteImageResponse updateText(SiteImageSlot slot, String headingMk, String headingEn, String textMk, String textEn) {
        SiteImage siteImage = siteImageRepository.findById(slot)
                .orElse(new SiteImage(slot, null, null));

        siteImage.setHeadingMk(headingMk);
        siteImage.setHeadingEn(headingEn);
        siteImage.setTextMk(textMk);
        siteImage.setTextEn(textEn);
        SiteImage saved = siteImageRepository.save(siteImage);
        return toResponse(saved);
    }

    private SiteImageResponse toResponse(SiteImage siteImage) {
        return new SiteImageResponse(
                siteImage.getSlot().name(),
                siteImage.getImageUrl(),
                siteImage.getHeadingMk(),
                siteImage.getHeadingEn(),
                siteImage.getTextMk(),
                siteImage.getTextEn());
    }
}