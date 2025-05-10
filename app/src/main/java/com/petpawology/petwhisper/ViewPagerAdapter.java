package com.petpawology.petwhisper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.petpawology.petwhisper.petinfo.EnterPetInfoFragment;
import com.petpawology.petwhisper.petinfo.FragmentSelectPetNotifications;


public class ViewPagerAdapter extends FragmentStateAdapter {
    private final Bundle bundle;


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Bundle bundle) {
        super(fragmentActivity);
        this.bundle = bundle;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if (position == 0) {
            EnterPetInfoFragment infoFragment = new EnterPetInfoFragment();
            infoFragment.setArguments(this.bundle);
            fragment = infoFragment;
        } else {
            FragmentSelectPetNotifications notifFragment = new FragmentSelectPetNotifications();
            notifFragment.setArguments(this.bundle);
            fragment = notifFragment;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
