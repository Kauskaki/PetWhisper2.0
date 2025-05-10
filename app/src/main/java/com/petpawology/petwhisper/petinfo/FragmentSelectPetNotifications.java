package com.petpawology.petwhisper.petinfo;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.petpawology.petwhisper.NotificationType;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.petpawology.petwhisper.NotifCardViewAdapter;
import com.petpawology.petwhisper.NotifPreferenceAdapter;
import com.petpawology.petwhisper.Pet;
import com.petpawology.petwhisper.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentSelectPetNotifications extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    LinearLayout linearLayout;

    ExpandableListView expandableListView;
    List<String> groupTitles;
    Map<String, List<NotifPreferenceAdapter.NotificationItem>> notifData;
    NotifPreferenceAdapter adapter2;

    LinearLayout linearLayoutPickNotifType;

    RecyclerView recyclerViewSelectNotifType;



    public Pet pet;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.enter_pet_info_select_notifs_fragment, container, false);
    }

    public FragmentSelectPetNotifications(Pet pet) {
        //empty Constructor
        this.pet = pet;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        expandableListView = view.findViewById(R.id.content_Notif_sections);

        linearLayoutPickNotifType = view.findViewById(R.id.linearLayout_SelectNotifs);

        recyclerViewSelectNotifType = view.findViewById(R.id.recycler_view_SelectNotifs);
        recyclerViewSelectNotifType.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        Log.d("DEBUG", "RecyclerView instance: " + recyclerViewSelectNotifType);


        List<NotificationType> items = Arrays.asList(
                new NotificationType("Mealtime", R.drawable.food_ic),
                new NotificationType("Medicine", R.drawable.medical_ic),
                new NotificationType("Vaccine", R.drawable.vaccination_ic),
                new NotificationType("Other", R.drawable.other_ic)
        );
        NotifCardViewAdapter adapter = new NotifCardViewAdapter(requireContext(),items);
        recyclerViewSelectNotifType.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Log.d("DEBUG", "RecyclerView item count: " + items.size());
        Log.d("DEBUG", "RecyclerView width: " + recyclerViewSelectNotifType.getWidth());
        Log.d("DEBUG", "RecyclerView height: " + recyclerViewSelectNotifType.getHeight());







        groupTitles = Arrays.asList("Mealtime", "Medicine", "Vaccine", "Other");



        notifData = new HashMap<>();
        notifData.put("Mealtime", Arrays.asList(
                new NotifPreferenceAdapter.NotificationItem("Remind me before mealtime", true)
        ));
        notifData.put("Medicine", Arrays.asList(
                new NotifPreferenceAdapter.NotificationItem("Remind me to give medicine", true)
        ));
        notifData.put("Vaccine", Arrays.asList(
                new NotifPreferenceAdapter.NotificationItem("Remind me for vaccine schedules", true)
        ));
        notifData.put("Other", Arrays.asList(
                new NotifPreferenceAdapter.NotificationItem("Other reminders", true)
        ));


        adapter2 = new NotifPreferenceAdapter(requireContext(), groupTitles, notifData);
        expandableListView.setAdapter(adapter2);

        /*
        radioButtonMonday.setOnClickListener(v -> {
    v.animate().scaleX(1.15f).scaleY(1.15f).alpha(0.8f).setDuration(150).withEndAction(() ->
        v.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(150)
    ).start();
});
         */




        }

}
