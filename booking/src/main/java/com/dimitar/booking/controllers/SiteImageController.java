package com.dimitar.booking.controllers;

import com.dimitar.booking.dto.SiteImageResponse;
import com.dimitar.booking.dto.SiteImageTextRequest;
import com.dimitar.booking.entities.SiteImageSlot;
import com.dimitar.booking.services.SiteImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/site-images")
public class SiteImageController {

    private final SiteImageService siteImageService;

    public SiteImageController(SiteImageService siteImageService) {
        this.siteImageService = siteImageService;
    }

    @GetMapping
    public ResponseEntity<List<SiteImageResponse>> getImages() {
        return ResponseEntity.ok(siteImageService.getAllImages());
    }

    @PostMapping("/{slot}")
    public ResponseEntity<SiteImageResponse> uploadImage(@PathVariable SiteImageSlot slot,
                                                         @RequestParam("file") MultipartFile file) {
        SiteImageResponse response = siteImageService.uploadImage(slot, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{slot}/text")
    public ResponseEntity<SiteImageResponse> updateText(@PathVariable SiteImageSlot slot,
                                                        @RequestBody SiteImageTextRequest request) {
        SiteImageResponse response = siteImageService.updateText(
                slot, request.getHeadingMk(), request.getHeadingEn(), request.getTextMk(), request.getTextEn());
        return ResponseEntity.ok(response);
    }
}