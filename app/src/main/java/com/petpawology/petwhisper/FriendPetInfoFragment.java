package com.petpawology.petwhisper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

public class FriendPetInfoFragment extends Fragment {
    private static final String ARG_PET = "pet";

    private Pet pet;

    public static FriendPetInfoFragment newInstance(Pet pet) {
        FriendPetInfoFragment fragment = new FriendPetInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PET, (Serializable) pet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pet = (Pet) getArguments().getSerializable(ARG_PET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.friend_pet_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView petImage = view.findViewById(R.id.pet_image);
        TextView name = view.findViewById(R.id.pet_name);
        TextView breed = view.findViewById(R.id.pet_breed);
        TextView birthday = view.findViewById(R.id.pet_birthday);
        TextView age = view.findViewById(R.id.pet_age);

        name.setText(pet.getName());
        breed.setText("Breed: " + pet.getBreed());
        birthday.setText("Birthday: " + pet.getBirthday());

        // Calculate and display age
        String petAge = pet.getBirthday();
        age.setText("Age: " + petAge + " years");

        //petImage.setImageResource(pet.getImageRes()); // Assuming drawable resource
    }

}
