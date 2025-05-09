package com.petpawology.petwhisper.petinfo;
import android.content.Context;

import java.util.Objects;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.petpawology.petwhisper.PetAdapter;
import com.petpawology.petwhisper.PetInfo;
import com.petpawology.petwhisper.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class EnterPetInfoFragment extends Fragment {
    private Intent intent;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    //Dialog: Select Pet's Species
    private DatePickerDialog datePickerbdayDialog;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    //Set Birthday
    private TextView bdayButton;

    //Pet Profile Picture
    private ShapeableImageView shapeablePet_pfp;


    //Species Remember Dialog Selection
    private String selectedSpecies;
    AutoCompleteTextView BreedDropdown;
    private String[] breeds;



    //Making a editText noneditible by default Unless the Pet's species is not listed
    private EditText edit_species_maybe;

    private AutoCompleteTextView breedTextView;

    //Save Pet Info
    LinearLayout SavePetInfo;


    //Adapter for Pet Selection Dialog
    private PetAdapter petAdapter;

    //Created when a Pet's species is not recognized
    private String[] newSpeciesList=  new String[]{};
    private String[] newBreedList = new String[]{};

    //Medical Info

    //Buttons to add Medical Info
    private ImageButton AddPetMeds;
    private ImageButton AddPetVaccines;
    private ImageButton AddPetAllergy;

    private static final int PICK_IMAGE_REQUEST = 1;

    //temp Medicine Values
    String tempMedName;
    String tempDosage;
    String tempExpirationDate;
    String tempType;
    String tempNotes;

    ArrayList<PetInfo> petInfoList = new ArrayList<>();

    List<PetInfo.Medication> tempMedList = new ArrayList<>();
    List<PetInfo.Vaccine> tempVaccineList = new ArrayList<>();
    ArrayList<PetInfo.Allergy> tempAllergyList = new ArrayList<>();
    MedicationAdapter adapterMeds;


    //Recycler Views
    ListView listViewMeds;
    ListView listViewVaccine;

    ListView listViewAllergy;





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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BreedDropdown = view.findViewById(R.id.BreedDropdown);
        edit_species_maybe = view.findViewById(R.id.pet_species_type);
        AddPetMeds = view.findViewById(R.id.AddPetMeds);
        AddPetVaccines = view.findViewById(R.id.AddPetVaccine);
        AddPetAllergy = view.findViewById(R.id.AddPetAllergies);
        SavePetInfo = view.findViewById(R.id.SavePetInfo);

        listViewVaccine = view.findViewById(R.id.vaccineList);
        listViewAllergy = view.findViewById(R.id.allergyList);
        if (listViewMeds == null) {
            Log.e("DEBUG", "listViewMeds is NULL");
        }



        //Check if Bundle was properly passed
        if (getArguments() != null) {
            selectedSpecies = getArguments().getString("selected_species", "None Selected");
            Log.d("DebugCheck", "EnterPetInfoFragment received species: " + selectedSpecies);
        } else {
            Log.e("DebugCheck", "Arguments bundle is NULL in EnterPetInfoFragment!");
            selectedSpecies = "None Selected"; // Prevent null errors
        }

        updateBreedDropdown(selectedSpecies, BreedDropdown);

        String[] options = {"Male", "Female", "Unsure"};
        Spinner spinner = view.findViewById(R.id.gender_spinner);
        spinner.setDropDownVerticalOffset(20);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        tempMedList = new ArrayList<>();
        tempMedList.add(new PetInfo.Medication("Test", "TestT", "Test", "Test", "Test"));
        adapterMeds = new MedicationAdapter(requireContext(), tempMedList);
        listViewMeds.setAdapter(adapterMeds);




    }


    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enter_pet_info_details_fragment, container, false);


        bdayButton = view.findViewById(R.id.SelectBdayButton);
        BreedDropdown = view.findViewById(R.id.BreedDropdown);
        edit_species_maybe = view.findViewById(R.id.pet_species_type);
        AddPetMeds = view.findViewById(R.id.AddPetMeds);
        AddPetVaccines = view.findViewById(R.id.AddPetVaccine);
        AddPetAllergy = view.findViewById(R.id.AddPetAllergies);
        SavePetInfo = view.findViewById(R.id.SavePetInfo);

        listViewMeds = view.findViewById(R.id.medList);


        bdayButton.setOnClickListener(v -> {
            Log.d("ClickDebug", "Opening Date Picker");
            datePicker();
        });

        //Making a text noneditible by default Unless the Pet is not listed
        edit_species_maybe = view.findViewById(R.id.pet_species_type);
        edit_species_maybe.setEnabled(false);
        breedTextView = view.findViewById(R.id.BreedDropdown);

        BreedDropdown = view.findViewById(R.id.BreedDropdown);
        updateBreedDropdown(selectedSpecies, BreedDropdown);

        //adjusting Pet pfp
        shapeablePet_pfp = view.findViewById(R.id.shapeableImageView);
        shapeablePet_pfp.setStrokeColor(getResources().getColorStateList(R.color.black, requireContext().getTheme()));

        shapeablePet_pfp.setOnClickListener(v -> {
            Log.d("ImagePicker", "Profile picture clicked");
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });



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

        // Open Pet Medication Dialog
        AddPetMeds.setOnClickListener(v -> {
            Log.d("DebugCheck", "Add Pet Medication Button Clicked");

            Dialog dialogMeds = new Dialog(requireContext(), R.style.DialogStyle);
            Objects.requireNonNull(dialogMeds.getWindow()).setBackgroundDrawableResource(R.drawable.enter_pet_info_container_bg);



            // Adjusting Size
            WindowManager.LayoutParams params = dialogMeds.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT; // Full width
            params.height = WindowManager.LayoutParams.WRAP_CONTENT; // Dynamic height
            dialogMeds.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialogMeds.getWindow().setAttributes(params);

            // Inflate the layout
            LayoutInflater inflaterMeds = getLayoutInflater();
            View popupView = inflaterMeds.inflate(R.layout.dialog_enter_medication, null);
            dialogMeds.setContentView(popupView);

            // **Fix: Reference UI elements from popupView**
            Spinner medicationTypeSpinner = popupView.findViewById(R.id.medicineType_spinner);
            TextView buttonCancel = popupView.findViewById(R.id.buttonCancelMeds);
            EditText editMedicationName = popupView.findViewById(R.id.editMedicationName);
            EditText editMedicationDosage = popupView.findViewById(R.id.editMedicationDosage);
            TextView buttonSaveMedication = popupView.findViewById(R.id.buttonSaveMedication);
            EditText MedicationNotes = popupView.findViewById(R.id.editMedicationNotes);
            TextView expirationDatePicker = popupView.findViewById(R.id.expirationDatePicker_meds);

            List<String> medicationTypes = Arrays.asList(popupView.getResources().getStringArray(R.array.MedicationType));
            ArrayAdapter<String> adapterMed = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    medicationTypes
            );
            adapterMed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            medicationTypeSpinner.setAdapter(adapterMed);

            // Debug Adapter Count
            Log.d("DebugCheck", "Adapter count: " + adapterMed.getCount());

            // Force Dropdown Appearance
            medicationTypeSpinner.post(() -> medicationTypeSpinner.performClick());

            // **Fix: Make Cancel Button Clickable**
            buttonCancel.setClickable(true);
            buttonCancel.setOnClickListener(view1 -> {
                Log.d("DebugCheck", "Cancel button clicked!");
                dialogMeds.dismiss();
            });

            expirationDatePicker.setOnClickListener(view1 -> {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Correct Builder Initialization
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Expiration Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

                // Listen for date selection
                datePicker.addOnPositiveButtonClickListener(selection -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(new Date(selection));

                    expirationDatePicker.setText(selectedDate); // Display selected date
                    Log.d("DatePickerDebug", "Selected Date: " + selectedDate);
                });

                // **Show the picker**
                datePicker.show(getParentFragmentManager(), "DATE_PICKER");
            });

            buttonSaveMedication.setOnClickListener(view1 -> {
                //Save Pet Info
                Log.d("DebugCheck", "Save Pet Medication Button Clicked");
                tempMedName = editMedicationName.getText().toString();
                tempDosage = editMedicationDosage.getText().toString();
                tempExpirationDate = expirationDatePicker.getText().toString();
                tempType = medicationTypeSpinner.getSelectedItem().toString();
                tempNotes= MedicationNotes.getText().toString();
                // Ensure no empty name
                if (tempMedName.isEmpty()) {
                    tempMedName = "Unnamed Medication";
                }

                // **When adding a new medication**
                PetInfo.Medication newMed = new PetInfo.Medication(tempMedName, tempType, tempDosage, tempExpirationDate, tempNotes);
                tempMedList.add(newMed);
                adapterMeds.notifyDataSetChanged();

                dialogMeds.dismiss();
            });


            // Show Dialog
            dialogMeds.show();
        });

        //Open Medicaiton Dialog using info from bundle

        listViewMeds.setOnItemClickListener((parent, view1, position, id) -> {
            PetInfo.Medication selectedMed = tempMedList.get(position);

            Dialog dialogMeds = new Dialog(requireContext(), R.style.DialogStyle);
            Objects.requireNonNull(dialogMeds.getWindow()).setBackgroundDrawableResource(R.drawable.enter_pet_info_container_bg);

            // Inflate the layout
            LayoutInflater inflater3 = getLayoutInflater();
            View popupView = inflater3.inflate(R.layout.dialog_enter_medication, null);
            dialogMeds.setContentView(popupView);

            // Get UI elements
            EditText editMedicationName = popupView.findViewById(R.id.editMedicationName);
            EditText editMedicationDosage = popupView.findViewById(R.id.editMedicationDosage);
            TextView expirationDatePicker = popupView.findViewById(R.id.expirationDatePicker_meds);
            EditText medicationNotes = popupView.findViewById(R.id.editMedicationNotes);
            Spinner medicationTypeSpinner = popupView.findViewById(R.id.medicineType_spinner);

            // Set previous values and disable editing
            editMedicationName.setText(selectedMed.getMedName());
            editMedicationDosage.setText(selectedMed.getDosage());
            expirationDatePicker.setText(selectedMed.getExpirationDate());
            medicationNotes.setText(selectedMed.getInstruction());
            //medicationTypeSpinner.setSelection(tempMedList.get(selectedMed.get()));

            // Show dialog
            dialogMeds.show();
        });

        //Open Pet Vaccine Dialog
        AddPetVaccines.setOnClickListener(v -> {
            Log.d("DebugCheck", "Add Pet Vaccine Button Clicked");
            Dialog dialogVaccine = new Dialog(requireContext(), R.style.DialogStyle);

            Objects.requireNonNull(dialogVaccine.getWindow()).setBackgroundDrawableResource(R.drawable.enter_pet_info_container_bg);
            // Adjusting Size
            WindowManager.LayoutParams params = dialogVaccine.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT; // Full width
            params.height = WindowManager.LayoutParams.WRAP_CONTENT; // Dynamic height
            dialogVaccine.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialogVaccine.getWindow().setAttributes(params);

            // Inflate the layout
            LayoutInflater inflaterMeds = getLayoutInflater();
            View popupView = inflaterMeds.inflate(R.layout.dialog_enter_vaccine, null);
            dialogVaccine.setContentView(popupView);

            TextView buttonCancelVaccine = popupView.findViewById(R.id.buttonCancelVaccine);
            EditText editVaccineName = popupView.findViewById(R.id.editVaccineName);
            TextView editVaccineEffectiveDate = popupView.findViewById(R.id.editVaccineEffectiveDate);
            TextView editVaccineExpirationDate = popupView.findViewById(R.id.editVaccineExpirationDate);
            EditText editVaccineNotes = popupView.findViewById(R.id.editVaccineNotes);
            TextView buttonSaveVaccine = popupView.findViewById(R.id.buttonSaveVaccine);

            // **Fix: Make Cancel Button Clickable**
            buttonCancelVaccine.setClickable(true);
            buttonCancelVaccine.setOnClickListener(view1 -> {
                Log.d("DebugCheck", "Cancel button clicked!");
                dialogVaccine.dismiss();
            });

            editVaccineExpirationDate.setOnClickListener(view1 -> {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Correct Builder Initialization
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Vaccine Expiration Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

                // Listen for date selection
                datePicker.addOnPositiveButtonClickListener(selection -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(new Date(selection));
                    editVaccineExpirationDate.setText(selectedDate); // Display selected date
                    Log.d("DatePickerDebug", "Selected Expiration Date: " + selectedDate);
                });

                // **Show the picker**
                datePicker.show(getParentFragmentManager(), "DATE_PICKER");
            });

            editVaccineEffectiveDate.setOnClickListener(view1 -> {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Correct Builder Initialization
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Vaccine Effective Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

                // Listen for date selection
                datePicker.addOnPositiveButtonClickListener(selection -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(new Date(selection));

                    editVaccineEffectiveDate.setText(selectedDate); // Display selected date
                    Log.d("DatePickerDebug", "Selected Effective Date: " + selectedDate);
                });

                // **Show the picker**
                datePicker.show(getParentFragmentManager(), "DATE_PICKER");
            });


            buttonSaveVaccine.setOnClickListener(view1 -> {
                Log.d("DebugCheck", "Save Pet Vaccine Button Clicked");
                String tempVaccineName = editVaccineName.getText().toString();
                String tempVaccineEffectiveDate = editVaccineEffectiveDate.getText().toString();
                String tempVaccineExpirationDate = editVaccineExpirationDate.getText().toString();
                String tempVaccineNotes = editVaccineNotes.getText().toString();
                // Ensure no empty name
                if (tempVaccineName.isEmpty()) {
                    tempVaccineName = "Unnamed Vaccine";
                }
                //temp vaccine class
                PetInfo.Vaccine  newVaccine = new PetInfo.Vaccine(tempVaccineName, tempVaccineEffectiveDate, tempVaccineExpirationDate, tempVaccineNotes);



                dialogVaccine.dismiss();

            });


            dialogVaccine.show();
        });

        //Open Pet Allergy Dialog
        AddPetAllergy.setOnClickListener(v -> {
            Log.d("DebugCheck", "Add Pet Allergy Button Clicked");
            Dialog dialogAllergy = new Dialog(requireContext(), R.style.DialogStyle);

            Objects.requireNonNull(dialogAllergy.getWindow()).setBackgroundDrawableResource(R.drawable.enter_pet_info_container_bg);
            //Adjusting Size
            WindowManager.LayoutParams params = dialogAllergy.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT; // Full width
            params.height = WindowManager.LayoutParams.WRAP_CONTENT; // Adjust height dynamically
            dialogAllergy.getWindow().setAttributes(params);
            //End Adjusting Size

            LayoutInflater inflaterAllergy = getLayoutInflater();
            View popupViewAllergy = inflaterAllergy.inflate(R.layout.dialog_enter_allergies, null);
            dialogAllergy.setContentView(popupViewAllergy); // Attach the layout to the dialog

            TextView buttonCancelAllergy = popupViewAllergy.findViewById(R.id.buttonCancelAllergy);
            EditText editAllergyName = popupViewAllergy.findViewById(R.id.editAllergyName);
            TextView buttonSaveAllergy = popupViewAllergy.findViewById(R.id.buttonSaveAllergy);
            EditText AllergyNotes = popupViewAllergy.findViewById(R.id.editAllergyNotes);
            ImageView trash_allergy = popupViewAllergy.findViewById(R.id.trash_allergy);

            SavePetInfo.setOnClickListener(view1 -> {
                Log.d("DebugCheck", "Save Pet Allergy Button Clicked");
                String tempAllergyName = editAllergyName.getText().toString();
                String tempAllergyNotes = AllergyNotes.getText().toString();

                // Ensure no empty name
                if (tempAllergyName.isEmpty()) {
                    tempAllergyName = "Unnamed Allergy";
                } else{
                    PetInfo.Allergy newAllergy = new PetInfo.Allergy(tempAllergyName, tempAllergyNotes);

                }

            });

            buttonCancelAllergy.setOnClickListener(view1 ->{
                Log.d("DebugCheck", "Cancel button clicked!");
                dialogAllergy.dismiss();

            });



            dialogAllergy.show();
        });







        return view;

    }

    //Adjust Pet Profile Image
    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    shapeablePet_pfp.setImageURI(imageUri); // Display selected image
                }
            });


    //Birthday Calendar
    private void datePicker() {
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

        datePicker.show(requireActivity().getSupportFragmentManager(), "DATE_PICKER");
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

    private void updateBreedDropdown(String species, AutoCompleteTextView breedDropdown) {
        if (species == null || species.isEmpty()) {
            Log.e("DebugCheck", "Species is NULL or empty!");
            return; // Exit early to avoid further errors
        }

        if (breedDropdown == null) {
            Log.e("DebugCheck", "BreedDropdown is NULL!");
            return;
        }

        breedDropdown.setText(""); // Clear previous selection

        switch (species) {
            case "Dog":
                breeds = getResources().getStringArray(R.array.dog_breeds);
                edit_species_maybe.setText(R.string.Dog);
                break;
            case "Cat":
                breeds = getResources().getStringArray(R.array.cat_breeds);
                edit_species_maybe.setText(R.string.Cat);
                break;
            case "Bird":
                breeds = getResources().getStringArray(R.array.bird_breeds);
                edit_species_maybe.setText(R.string.Bird);
                break;
            case "Rabbit":
                breeds = getResources().getStringArray(R.array.rabbit_breeds);
                edit_species_maybe.setText(R.string.Rabbit);
                break;
            default:
                Log.d("DebugCheck", "Species Not Recognized");
                breeds = newBreedList != null ? newBreedList : new String[]{"Enter Breed"};
                edit_species_maybe.setEnabled(true);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, breeds);
        breedDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class MedicationAdapter extends ArrayAdapter<PetInfo.Medication> {
        private final Context context;
        private final List<PetInfo.Medication> medicationList;

        public MedicationAdapter(Context context, List<PetInfo.Medication> medicationList) {
            super(context, R.layout.enter_pet_info_items_display, medicationList);
            this.context = context;
            this.medicationList = medicationList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.enter_pet_info_items_display, parent, false);
            }

            TextView medName = convertView.findViewById(R.id.DisplayMedName);
            TextView medDosage = convertView.findViewById(R.id.DisplayMedDosage);

            PetInfo.Medication medication = medicationList.get(position);
            medName.setText(medication.getMedName());
            medDosage.setText("Dosage: " + medication.getDosage());

            return convertView;
        }
    }

}
