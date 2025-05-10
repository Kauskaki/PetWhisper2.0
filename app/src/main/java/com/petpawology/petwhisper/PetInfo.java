package com.petpawology.petwhisper;

import android.os.Bundle;
import android.os.Parcelable;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PetInfo {

    //Pet Info gathering.
    private int petImage;
    private String petName;
    private String petBreed;
    private String petGender;
    private int petAge;
    private String petId;
    private String petBirthday;

    private ShapeableImageView pet_pfp;

    private Map<String, Long> notifications = new HashMap<>();
    private String Notification_Title;
    private String Notification_Description;
    private ArrayList<String> pet_Allergies = new ArrayList<>();

    private ArrayList<Medication> pet_MedicationsRecords;
    private ArrayList<Vaccine>pet_VaccinesRecords;
    private ArrayList<Allergy>pet_AllergiesRecords;


    //Pet Dialog selection screen only
    private String speciesName;
    private int imageResId;

    //Vistor Boolean
    private boolean visitor = false;

    int ctl_arraylist_size = 0;





    public PetInfo(String petName, String petBreed, String petGender, int petAge, String birthday, int petImage, boolean visitor, ArrayList<Vaccine> vaccineList, ArrayList<Medication> medicationList, ArrayList<Allergy> allergyList) {
        if (petId == null || petId.isEmpty()) {
            petId = UUID.randomUUID().toString();
        }
        this.petImage = petImage;
        this.petName = petName;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.petBirthday = birthday;

        this.petAge = petAge;
        if (vaccineList != null) {

            this.pet_VaccinesRecords = vaccineList;
        } else {
            this.pet_VaccinesRecords = new ArrayList<>();
        }
        if (allergyList != null) {
            this.pet_AllergiesRecords = allergyList;
        } else {
            this.pet_AllergiesRecords = new ArrayList<>();
        }

        if (medicationList != null) {
            this.pet_MedicationsRecords = medicationList;
        } else{
            this.pet_MedicationsRecords = new ArrayList<>();
        }
        this.visitor = visitor;
    }

    public boolean getVisitorStatus() {
        return visitor;
    }


    //Animal ArrayList Dialog Options | ONlY USED IN PET DIALOG | Holds ony an image and Species Type in a caradview
    public PetInfo(String speciesName, int  imageResId) {
        this.imageResId = imageResId;
        this.speciesName = speciesName;
    }

    public void displayPetInfo(){
        this.petName = petName;
        this.petImage = petImage;
        this.petAge = petAge;
    }

    //Getters
    public int getPetImage() {
        return petImage;
    } //This is to hold image of the Pet

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
    } //This is used for Animal Dialog ONLY

    public String getSpeciesName() {
        return speciesName;
    }

    public String getPetBirthday() {
        return petBirthday;
    }
    public String getPetId() {
        return petId;
    }
    public ArrayList<Allergy> getPetAllergies() {
        return pet_AllergiesRecords;
    }
    public ArrayList<Medication> getPetMedications() {
        return pet_MedicationsRecords;
    }
    public ArrayList<Vaccine> getPetVaccines() {
        return pet_VaccinesRecords;
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

    public void setNotificationTitle(String title) {
        this.Notification_Title = title;
    }
    public void setNotificationDescription(String description) {
        this.Notification_Description = description;
    }

    public void addVaccine(String name, String Exdate,String effdate, String type, String notes) {
        pet_VaccinesRecords.add(new Vaccine(name, Exdate, effdate, notes));
    }






    public static class Vaccine {
        String name;
        String Expirationdate;
        String EffectiveDate;
        String notes;

        public Vaccine(String name, String Exdate,String EffectiveDate, String notes) {
            this.name = name;
            this.Expirationdate = Exdate;
            this.EffectiveDate = EffectiveDate;
            this.notes = notes;
        }
        public String getVacName() {
            return name;
        }
        public String getExpirationDate() {
            return Expirationdate;
        }
        public String getEffectiveDate() {
            return EffectiveDate;
        }
        public String getNotes() {
            return notes;
        }
        public void setVacName(String name) {
            this.name = name;
        }
        public void setVacExpirationDate(String Exdate) {
            this.Expirationdate = Exdate;
        }
        public void setVacEffectiveDate(String EffectiveDate) {
            this.EffectiveDate = EffectiveDate;
        }
        public void setVacNotes(String notes) {
            this.notes = notes;
        }
    }

    public static class Notification {
        String name;
        String type;
        long time;
    }

    public static class Allergy {
        String name;
        String Notes;

        public Allergy(String name, String notes) {
            this.name = name;
            this.Notes = notes;

        }

        public String getAllergyName() {
            return name;
        }

        public String getAllergyNotes() {
            return Notes;
        }

        public void setAllergyName(String name) {
            this.name = name;
        }

        public void setAllergyNotes(String notes) {
            this.Notes = notes;
        }
    }

    public static class Medication {
        String MedName;
        int Medtype;
        String dosage;
        String Instruction;

        String ExpirationDate;

        public Medication(String MedName, int Medtype, String dosage, String ExpirationDate, String Instruction) {
            this.MedName = MedName;
            this.Medtype = Medtype;
            this.dosage = dosage;
            this.ExpirationDate = ExpirationDate;
            this.Instruction = Instruction;
        }

        //getter
        public String getMedName() {
            return MedName;
        }
        public int getMedtype() {
            return Medtype;
        }
        public String getMedDosage() {
            return dosage;
        }
        public String getMedExpirationDate() {
            return ExpirationDate;
        }
        public String getMedInstruction() {
            return Instruction;
        }

        //Setters
        public void setMedName(String MedName) {
            this.MedName = MedName;
        }
        public void setMedtype(int Medtype) {
            this.Medtype = Medtype;
        }
        public void setMedDosage(String dosage) {
            this.dosage = dosage;
            }
        public void setMedExpirationDate(String ExpirationDate) {
            this.ExpirationDate = ExpirationDate;
        }
        public void setMedInstruction(String Instruction) {
            this.Instruction = Instruction;
        }

    }

    public Bundle holdPetInfo() {
        Bundle bundle = new Bundle();
        bundle.putString("petName", petName);
        bundle.putString("petBreed", petBreed);
        bundle.putString("petGender", petGender);
        bundle.putInt("petAge", petAge);
        bundle.putString("birthday", petBirthday);
        bundle.putInt("petImage", petImage);
        bundle.putBoolean("visitor", visitor);
        bundle.putString("petId", petId);

        bundle.putSerializable("vaccineList", pet_VaccinesRecords);
        bundle.putSerializable("medicationList", pet_MedicationsRecords);
        bundle.putSerializable("allergyList", pet_AllergiesRecords);

        return bundle;
    }


}

