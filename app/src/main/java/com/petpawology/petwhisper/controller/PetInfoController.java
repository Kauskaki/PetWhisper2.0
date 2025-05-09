package com.petpawology.petwhisper.controller;

import com.petpawology.petwhisper.PetInfo;
import com.petpawology.petwhisper.petinfo.EnterPetInfoFragment;

import java.util.HashMap;

public class PetInfoController {
    private com.petpawology.petwhisper.PetInfo PetInfo;
    private EnterPetInfoFragment fragmentView;


    public PetInfoController(PetInfo model, EnterPetInfoFragment view) {
        this.PetInfo = model;
        this.fragmentView = view;
    }


    public void updatePetName(String name) {
        PetInfo.setPetName(name);  // Update data in model
    }
    public void updatePetBreed(String breed) {
        PetInfo.setPetBreed(breed);  // Update data in model
    }

    public void updatePetGender(String gender) {
        PetInfo.setPetGender(gender);  // Update data in model
    }
    public void updatePetAge(int age) {
        PetInfo.setPetAge(age);  // Update data in model
    }
    public void updatePetImage(int image) {
        PetInfo.setPetImage(image);  // Update data in model
    }
    public void updatePetSpecies(String species) {
        PetInfo.setSpeciesName(species);  // Update data in model
    }

    public void updatePetImageResId(int imageResId) {
        PetInfo.setImageResId(imageResId);  // Update data in model
    }
    public String getPetName() {
        return PetInfo.getPetName();  // Get data from model
    }

    public String getPetBreed() {
        return PetInfo.getPetBreed();  // Get data from model
    }
    public String getPetGender() {
        return PetInfo.getPetGender();  // Get data from model
    }
    public int getPetAge() {
        return PetInfo.getPetAge();  // Get data from model
    }

}
