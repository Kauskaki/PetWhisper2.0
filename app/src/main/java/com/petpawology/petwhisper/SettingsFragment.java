package com.petpawology.petwhisper;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override

    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        if (findPreference("sync") == null) {
            Log.e("PreferenceDebug", "Sync preference missing!");
        }
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

    }

}