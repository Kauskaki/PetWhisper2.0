package com.petpawology.petwhisper.petinfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.petpawology.petwhisper.R;
import com.petpawology.petwhisper.ViewPagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentEnterPetInfoContainer extends Fragment {
        TabLayout tabLayout;
        ViewPager2 viewPager2;
        ViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.enter_pet_info_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve bundle from previous fragment (PetAdapter)
        Bundle bundle = getArguments();
        if (bundle != null) {
            Log.d("DebugCheck", "FragmentEnterPetInfoContainer received species: " + bundle.getString("selected_species"));
        } else {
            Log.e("DebugCheck", "Bundle is NULL in FragmentEnterPetInfoContainer!");
        }

        TabLayout tabLayout = view.findViewById(R.id.tabLayout_enterpetinfo);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2_enterpetinfo);

        // Set up adapter with the bundle
        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity(), bundle);
        viewPager2.setAdapter(adapter);

        // Attach TabLayoutMediator
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(position == 0 ? "Pet Info" : "Set Notifications")
        ).attach();
    }

}
