package com.petpawology.petwhisper.friends;
import com.petpawology.petwhisper.Pet;

import java.util.List;

public class Friend {
    private String name;
    private List<Pet> pets;

    public Friend(String name, List<Pet> pets) {
        this.name = name;
        this.pets = pets;
    }

    public String getName() {
        return name;
    }
    public Integer getImageResId(){
        return 1515;

    }
    public List<Pet> getPets() {
        return pets;
    }
}
