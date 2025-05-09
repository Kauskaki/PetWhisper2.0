package com.petpawology.petwhisper;

public class NotificationType {
    private String title;
    private int imageResId;



    public NotificationType(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public int getImageResId() { return imageResId; }



}
