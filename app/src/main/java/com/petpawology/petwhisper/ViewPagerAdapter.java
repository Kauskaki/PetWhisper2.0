package com.petpawology.petwhisper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.petpawology.petwhisper.petinfo.EnterPetInfoFragment;
import com.petpawology.petwhisper.petinfo.FragmentSelectPetNotifications;


public class ViewPagerAdapter extends FragmentStateAdapter {
    private Bundle fragmentArgs;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Bundle fragmentArgs) {
        super(fragmentActivity);
        this.fragmentArgs = fragmentArgs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new EnterPetInfoFragment();
                break;
            case 1:
                fragment = new FragmentSelectPetNotifications();
                break;
            default:
                fragment = new EnterPetInfoFragment();
                break;
        }

        // Pass the bundle to the fragment
        fragment.setArguments(fragmentArgs);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2; // Number of tabs
    }
}