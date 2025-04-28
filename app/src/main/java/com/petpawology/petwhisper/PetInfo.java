package com.petpawology.petwhisper;

import java.util.ArrayList;

public class PetInfo {

    //Pet Info gathering.
    int petImage;
    String petName;
    String petBreed;
    String petGender;
    int petAge;


    //Pet selection screen only
    private String speciesName;
    private int imageResId;

    int ctl_arraylist_size = 0;

    ArrayList<String> petAllgeries = new ArrayList<>();

    ArrayList<String> petMedications = new ArrayList<>();


    public PetInfo(int petImage, String petName, String petBreed, String petGender, int petAge) {
        //Pet image
        this.petImage = petImage;

        //Other pet info
        this.petName = petName;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.petAge = petAge;

    }


    //Animal ArraryList Dialog Options
    public PetInfo(String speciesName, int  imageResId) {
        this.imageResId = imageResId;
        this.speciesName = speciesName;
    }

    //Getters
    public int getPetImage() {
        return petImage;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public int getPetAge() {
        return petAge;
    }

    public String getPetName() {
        return petName;
    }

    public String getPetGender() {
        return petGender;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    //Setters
    public void setPetImage(int petImage) {
        this.petImage = petImage;
    }
    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }
    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }
    public void setPetName(String petName) {
        this.petName = petName;
    }
    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }
    public void setPetAllgeries(String enterAllergy) {
        petAllgeries.add(enterAllergy);
    }

    public void setPetMedications(String enterMedication) {
        petMedications.add(enterMedication);
    }
}

