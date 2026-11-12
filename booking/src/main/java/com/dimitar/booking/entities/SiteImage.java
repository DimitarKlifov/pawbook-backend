package com.dimitar.booking.entities;

import jakarta.persistence.*;

@Entity
public class SiteImage {

    @Id
    @Enumerated(EnumType.STRING)
    private SiteImageSlot slot;

    private String imageUrl;
    private String publicId;

    @Column(length = 200)
    private String headingMk;

    @Column(length = 200)
    private String headingEn;

    @Column(length = 2000)
    private String textMk;

    @Column(length = 2000)
    private String textEn;

    public SiteImage() {}

    public SiteImage(SiteImageSlot slot, String imageUrl, String publicId) {
        this.slot = slot;
        this.imageUrl = imageUrl;
        this.publicId = publicId;
    }

    public SiteImageSlot getSlot() { return slot; }
    public void setSlot(SiteImageSlot slot) { this.slot = slot; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getPublicId() { return publicId; }
    public void setPublicId(String publicId) { this.publicId = publicId; }
    public String getHeadingMk() { return headingMk; }
    public void setHeadingMk(String headingMk) { this.headingMk = headingMk; }
    public String getHeadingEn() { return headingEn; }
    public void setHeadingEn(String headingEn) { this.headingEn = headingEn; }
    public String getTextMk() { return textMk; }
    public void setTextMk(String textMk) { this.textMk = textMk; }
    public String getTextEn() { return textEn; }
    public void setTextEn(String textEn) { this.textEn = textEn; }
}