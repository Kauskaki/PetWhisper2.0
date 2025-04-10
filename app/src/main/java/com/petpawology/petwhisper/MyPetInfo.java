package com.petpawology.petwhisper;

import java.lang.reflect.Array;
public interface MyPetInfo {
    // Constants
    int MAX_MED = 10;


    // Method signatures (abstract methods)
    void setPetName(String petName);
    void setPetAge(int petAge);
    void setPetWeight(double petWeight);
    void setMedications(String[] medications);
    void addMedication(String medication);
    void addAllergies(String[] allergies);
    void addAllergies(String allergy);

    //Retrieve Pet information
    String getPetName();
    int getPetAge();
    double getPetWeight();
    String[] getMedications();

    //Retrieve Allergy information
    String[] getAllergies();





}
