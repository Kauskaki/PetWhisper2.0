package com.petpawology.petwhisper.petinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.petpawology.petwhisper.Pet;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Pet> pet = new MutableLiveData<>();

    public void setPet(Pet pet) {
        this.pet.setValue(pet);
    }

    public LiveData<Pet> getPet() {
        return pet;
    }
}
