package com.petpawology.petwhisper;

import android.net.Uri;
import android.util.Log;

import com.petpawology.petwhisper.Pet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImagesLoader {

    private static ImagesLoader instance;

    // Cache image URLs by pet name (or ID)
    private final Map<String, Uri> imageUriCache = new HashMap<>();

    private ImagesLoader() {}

    public static ImagesLoader getInstance() {
        if (instance == null) {
            instance = new ImagesLoader();
        }
        return instance;
    }

    // Load image URLs from your Pet objects
    public void cachePetImages(List<Pet> pets) {
        imageUriCache.clear();
        for (Pet pet : pets) {
            String imageUrl = pet.imageUrl; // This should return the full HTTPS link
            if (imageUrl != null && !imageUrl.isEmpty()) {
                imageUriCache.put(pet.getName(), Uri.parse(imageUrl));
            } else {
                Log.w("ImagesLoader", "No image URL for pet: " + pet.getName());
            }
        }
    }

    public Uri getImageUri(String petName) {
        return imageUriCache.get(petName);
    }
}
