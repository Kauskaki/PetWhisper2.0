package com.petpawology.petwhisper.main_fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.petpawology.petwhisper.PetAdapter;
import com.petpawology.petwhisper.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class FragmentEnterPetInfo extends Fragment {
    private Intent intent;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    //Dialog: Select Pet's Species
    private DatePickerDialog datePickerbdayDialog;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button bdayButton;


    //Species Remember Dialog Selection
    private String selectedSpecies;
    AutoCompleteTextView BreedDropdown;
    private String[] breeds;


    //Making a text noneditible by default Unless the Pet is not listed
    private EditText edit_species_maybe;


    //Adapter for Pet Selection
    private PetAdapter petAdapter;

    //Created when a species is not recognized
    private String[] newSpeciesList=  new String[]{};

    public void setPetAdapter(PetAdapter adapter) {
        this.petAdapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enterpetinfo, container, false);

        bdayButton = view.findViewById(R.id.SelectBdayButton);

        bdayButton.setOnClickListener(v -> {
            Log.d("ClickDebug", "Opening Date Picker");
            initDatePicker();
        });

        //Making a text noneditible by default Unless the Pet is not listed
        edit_species_maybe = view.findViewById(R.id.pet_species_type);
        edit_species_maybe.setEnabled(false);

        //Checking the info from the Pet Selection Dialog is successfully Transferred to this Fragment
        Log.d("DebugCheck", "FragmentEnterPetInfo Created");
        if (getArguments() != null) {
            selectedSpecies = getArguments().getString("selected_species", "None Selected");
            Log.d("DebugCheck", "Received species: " + selectedSpecies);
        } else {
            Log.e("DebugCheck", "Arguments bundle is NULL!");
        }

        BreedDropdown = view.findViewById(R.id.BreedDropdown);
        updateBreedDropdown(selectedSpecies, BreedDropdown);


        // Initialize the Species dropdown
        if (breeds == null) {
            breeds = new String[]{}; // Prevent null errors
        }
        ArrayAdapter<String> speciesAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, breeds);
        BreedDropdown.setAdapter(speciesAdapter);

        // Listen for species selection
        BreedDropdown.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedBreed = (String) parent.getItemAtPosition(position);
            Log.d("DebugCheck", "User selected breed: " + selectedBreed);

            BreedDropdown.setText(selectedBreed, false); // Ensures text is updated without triggering filtering
        });
        return view;

    }

    //Birthday Calendar
    private void initDatePicker() {
        Calendar today = Calendar.getInstance();

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selection);

            // Convert UTC to device local time
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Force UTC interpretation
            String formattedDate = sdf.format(calendar.getTime());

            calendar.setTimeZone(TimeZone.getDefault()); // Convert to local device time
            formattedDate = sdf.format(calendar.getTime());

            if (bdayButton != null) {
                bdayButton.setText(formattedDate);
            } else {
                Log.e("DebugCheck", "bdayButton is NULL!");
            }
        });
        datePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
    }

    private String makeDateString(int dayOfMonth, int month, int year){
        String date = getDateFormat(month) + " " + dayOfMonth + " " + year;
        return date;
    }

    private String getDateFormat(int month){
        switch (month){
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "JAN";
        }
    }
    //End Birthday Calendar

    private void updateBreedDropdown(String species, AutoCompleteTextView breedDropdown){
        if (species == null || species.isEmpty()) {
            Log.e("DebugCheck", "Species is NULL or empty!");

        }

        breedDropdown.setText(""); // Clear previous selection before setting new adapter


        switch (Objects.requireNonNull(species)) {
            case "Dog":
                breeds = getResources().getStringArray(R.array.dog_breeds);
                Log.d("DebugCheck", "Species Dog");
                edit_species_maybe.setText("Dog");

                break;
            case "Cat":
                breeds = getResources().getStringArray(R.array.cat_breeds);
                Log.d("DebugCheck", "Species Cat");
                edit_species_maybe.setText("Cat");
                break;
            case "Bird":
                breeds = getResources().getStringArray(R.array.bird_breeds);
                Log.d("DebugCheck", "Species Bird");
                edit_species_maybe.setText("Bird");
                break;
            case "Rabbit":
                breeds = getResources().getStringArray(R.array.rabbit_breeds);
                Log.d("DebugCheck", "Species Rabbit");
                edit_species_maybe.setText("Rabbit");
                break;

            default:
                Log.d("DebugCheck", "Species Not Recognized");

                BreedDropdown.setText("Enter Breed");
                breeds = // Empty if species not recognized
                edit_species_maybe.setEnabled(true);
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, breeds);
        breedDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



}
