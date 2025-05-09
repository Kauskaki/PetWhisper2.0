package com.petpawology.petwhisper;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private ShapeableImageView pet_pfp;

    private Map<String, Long> notifications = new HashMap<>(); // ✅ Stores notification type & time
    private String Notification_Title;
    private String Notification_Description;
    private ArrayList<String> pet_Allergies = new ArrayList<>();

    private List<Medication>pet_Medications;
    private List<Vaccine>pet_VaccinesRecords;
    private List<Allergy>pet_AllergiesRecords;


    //Pet Dialog selection screen only
    private String speciesName;
    private int imageResId;

    //Vistor Boolean
    private boolean visitor = false;

    int ctl_arraylist_size = 0;





    public PetInfo(String petName, String petBreed, String petGender, int petAge, int petImage, boolean visitor) {
        if (petId == null || petId.isEmpty()) {
            petId = UUID.randomUUID().toString();
        }
        this.imageResId = petImage;
        this.petName = petName;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.petAge = petAge;
        this.pet_VaccinesRecords = new ArrayList<>();
        this.pet_Medications = new ArrayList<>();
        this.pet_AllergiesRecords = new ArrayList<>();
        this.visitor = visitor;
    }

    public boolean getVisitorStatus() {
        return visitor;
    }


    //Animal ArrayList Dialog Options
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
    public ArrayList<String> getPetAllergies() {
        return pet_Allergies;
    }

    public String getNotificationTitle() {
        return Notification_Title;
    }
    public String getNotificationDescription() {
        return Notification_Description;
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
        public String getName() {
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
        public String getName() {
            return name;
        }
        public String getNotes() {
            return Notes;
        }
    }

    public static class Medication {
        String MedName;
        String Medtype;
        String dosage;
        String Instruction;

        String ExpirationDate;

        public Medication(String MedName, String Medtype, String dosage, String ExpirationDate, String Instruction) {
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
        public String getMedtype() {
            return Medtype;
        }
        public String getDosage() {
            return dosage;
        }
        public String getExpirationDate() {
            return ExpirationDate;
        }
        public String getInstruction() {
            return Instruction;
        }
    }



}

