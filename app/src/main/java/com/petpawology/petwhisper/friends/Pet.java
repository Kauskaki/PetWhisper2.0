package com.petpawology.petwhisper.friends;

public class Pet {
    private String name;
    public String imageRes;

    public Pet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBreed(){
        return "breed";
    }
    public String getBirthday(){
        return "birthday";
    }
    public String getImageRes(){
        return"none";
    }

}
