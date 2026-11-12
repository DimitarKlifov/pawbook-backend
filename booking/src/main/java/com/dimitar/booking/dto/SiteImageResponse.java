package com.dimitar.booking.dto;

public class SiteImageResponse {
    private String slot;
    private String imageUrl;
    private String headingMk;
    private String headingEn;
    private String textMk;
    private String textEn;

    public SiteImageResponse(String slot, String imageUrl, String headingMk, String headingEn, String textMk, String textEn) {
        this.slot = slot;
        this.imageUrl = imageUrl;
        this.headingMk = headingMk;
        this.headingEn = headingEn;
        this.textMk = textMk;
        this.textEn = textEn;
    }

    public String getSlot() { return slot; }
    public String getImageUrl() { return imageUrl; }
    public String getHeadingMk() { return headingMk; }
    public String getHeadingEn() { return headingEn; }
    public String getTextMk() { return textMk; }
    public String getTextEn() { return textEn; }
}