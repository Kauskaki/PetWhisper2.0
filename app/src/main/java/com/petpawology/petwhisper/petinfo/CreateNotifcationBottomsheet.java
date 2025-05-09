package com.petpawology.petwhisper.petinfo;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.petpawology.petwhisper.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CreateNotifcationBottomsheet extends BottomSheetDialogFragment {
    private EditText editTextNotes;
    private EditText editText_notif_title;

    private EditText editText_repeatsOn;

    private TextView SaveNotifSettings;

    private ImageView dissmiss;

    private TimePickerDialog timePicker;


    private TextView setAlarm;

    private ConstraintLayout setAlarmContainer;

    String selectedTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.enter_pet_info_notifs_create_downsheet, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) view.getParent());

            // Set initial peek height (visible portion before expansion)
            behavior.setPeekHeight(600);

            // Allow full expansion when dragged up
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextNotes = view.findViewById(R.id.editTextNotes);
        editText_notif_title = view.findViewById(R.id.notif_title);
        editText_repeatsOn = view.findViewById(R.id.repeatsOn_number);
        setAlarm = view.findViewById(R.id.setTimeAlarm);
        SaveNotifSettings = view.findViewById(R.id.addAlarm);
        setAlarmContainer = view.findViewById(R.id.settingAlarm_container);


        //Dissmiss Button
        dissmiss = view.findViewById(R.id.dismissBtn);
        dissmiss.setOnClickListener(v -> {
            dismiss();
        });


        // Set up Spinner
        Spinner spinner = view.findViewById(R.id.timePeriodSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.RepeatOn_spinner, R.layout.custom_spinner_dropdown);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner.setAdapter(adapter);



        //Change Spinner Dynamically based on input
        editText_repeatsOn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputText = editText_repeatsOn.getText().toString().trim();

                try {
                    int number = Integer.parseInt(inputText);
                    Log.d("DEBUG", "Captured Number: " + number);
                    ArrayList<String> itemList = new ArrayList<>();
                    itemList.add("Weeks");
                    itemList.add("Months");
                    itemList.add("Years");

                    if (number > 1) {
                        ArrayAdapter<String> adapterTemp = new ArrayAdapter<>(requireContext(), R.layout.custom_font_spinner, itemList);
                        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);

                        spinner.setAdapter(adapterTemp);
                    } else{
                        spinner.setAdapter(adapter);
                    }
                } catch (NumberFormatException e) {
                    Log.e("ERROR", "Invalid input – Not a number!");
                }


            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Log.d("DEBUG", "Selected: " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        //Time Picker
        setAlarmContainer.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            // Click animation
            v.animate()
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .setStartDelay(0)
                    .setDuration(50)
                    .withEndAction(() -> v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100));

            timePicker = new TimePickerDialog(
                    requireContext(),
                    (pickerView, selectedHour, selectedMinute) -> {
                        boolean is24HourFormat = android.text.format.DateFormat.is24HourFormat(requireContext());

                        // ✅ Update the existing `Calendar` instance instead of creating a new one
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);

                        // Format the selected time correctly
                        SimpleDateFormat format = is24HourFormat
                                ? new SimpleDateFormat("HH:mm", Locale.getDefault()) // 24-hour format
                                : new SimpleDateFormat("hh:mm a", Locale.getDefault()); // 12-hour AM/PM format

                        String formattedTime = format.format(calendar.getTime());

                        // Smooth fade animation when updating the text
                        setAlarm.setAlpha(0f);
                        setAlarm.setText(formattedTime);
                        setAlarm.animate().alpha(1f).setDuration(300);
                    },
                    hour, minute, false // `false` enables AM/PM format
            );

            timePicker.show();
        });


        /*Save Button
        SaveNotifSettings.setOnClickListener(v -> {
            int hour = timePicker.getCurrentHour(); // API 18+
            int minute = timePicker.getCurrentMinute();

            boolean isImportant = checkboxImportant.isChecked();
            boolean isReminder = checkboxReminder.isChecked();

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

        });
        */





    }

}

