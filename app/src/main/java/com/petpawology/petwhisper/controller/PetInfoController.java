package com.petpawology.petwhisper.controller;

import com.petpawology.petwhisper.PetInfo;
import com.petpawology.petwhisper.main_fragments.FragmentEnterPetInfo;

public class PetInfoController {
    private com.petpawology.petwhisper.PetInfo PetInfo;
    private FragmentEnterPetInfo fragmentView;

    public PetInfoController(PetInfo model, FragmentEnterPetInfo view) {
        this.PetInfo = model;
        this.fragmentView = view;
    }

    public void updatePetName(String name) {
        PetInfo.setPetName(name);  // Update data in model
    }
}
