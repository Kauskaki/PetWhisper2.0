package com.petpawology.petwhisper;

import java.util.List;

public class Friend {
    private String id;
    private String name;
    private List<Pet> pets;
    private String imageResId;
    public Friend(String id,String name, List<Pet> pets) {
        this.id = id;
        this.name = name;
        this.pets = pets;
    }
    public String getName() {
        return name;
    }
    public String getImageResId(){
        return
        imageResId;
    }
    public List<Pet> getPets() {
        return pets;
    }
}
