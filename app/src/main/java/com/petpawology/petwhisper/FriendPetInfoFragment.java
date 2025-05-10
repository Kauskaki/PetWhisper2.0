package com.petpawology.petwhisper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.petpawology.petwhisper.friends.Pet;

public class FriendPetInfoFragment extends Fragment {
    private static final String ARG_PET = "pet";
    private Pet pet;

    public static FriendPetInfoFragment newInstance(Pet pet) {
        FriendPetInfoFragment fragment = new FriendPetInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PET, pet);
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
        TextView nameView = view.findViewById(R.id.pet_name);
        TextView breedView = view.findViewById(R.id.breedValue);
        TextView birthdayView = view.findViewById(R.id.birthDateView);
        TextView speciesView = view.findViewById(R.id.speciesValue);
        TextView genderView = view.findViewById(R.id.genderValue);

        // Set text values
        //nameView.setText(pet.getName());
        //breedView.setText(pet.getBreed());
        //birthdayView.setText(pet.getBirthday());
        //speciesView.setText(pet.getSpecies());
        //genderView.setText(pet.getGender());

        // Set pet image if you have a drawable or URL loading mechanism
        // petImage.setImageResource(pet.getImageResId());
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            Toolbar toolbar = activity.findViewById(R.id.AppBar);
            toolbar.setTitle("");
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setTitle("Pet Name");
            TextView toolbarTitle = activity.findViewById(R.id.toolbar_title);
            toolbarTitle.setText("");

        }
        setHasOptionsMenu(true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Pops the fragment and returns to FriendFragment
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            activity.getSupportActionBar().setTitle("");
            TextView toolbarTitle = activity.findViewById(R.id.toolbar_title);
            toolbarTitle.setText("Friends");
            requireActivity().getSupportFragmentManager().popBackStack();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
