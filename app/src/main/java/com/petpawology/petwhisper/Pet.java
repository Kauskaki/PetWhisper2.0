package com.petpawology.petwhisper;

import java.io.Serializable;

public class Pet implements Serializable {
    private String name;
    public String petId;
    public PetInfo petInfo;

    public Pet(String id) {
        this.name = name;
        this.petId = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public PetInfo getPetInfo() {
        return petInfo;
    }

    public void setPetInfo(PetInfo petInfo) {
        this.petInfo = petInfo;
    }

    public String getName() {
        return name;
    }



}
