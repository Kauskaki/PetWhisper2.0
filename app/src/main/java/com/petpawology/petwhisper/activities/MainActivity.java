package com.petpawology.petwhisper.activities;
import static android.app.PendingIntent.getActivity;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.petpawology.petwhisper.FriendFragment;

import com.petpawology.petwhisper.main_fragments.HomeListFragment;
import com.petpawology.petwhisper.petinfo.FragmentEnterPetInfoContainer;
import com.petpawology.petwhisper.userData;
import com.petpawology.petwhisper.PetAdapter;
import com.petpawology.petwhisper.PetInfo;
import com.petpawology.petwhisper.R;
import com.petpawology.petwhisper.main_fragments.SearchFragment;
import com.petpawology.petwhisper.main_fragments.SettingsFragment;
import com.petpawology.petwhisper.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private userData userData;
    private Intent intent;
    private FirebaseAuth mAuth;

    ImageButton backbutton;
    ShapeableImageView settingsIcon;
    Button logout;
    BottomNavigationView bottom_navigation;
    private FloatingActionButton addPet;

    Toolbar toolbar;







    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.MainFrameContainer, fragment).addToBackStack(null).commit();
    }

    ActivityMainBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.LightPurp));



        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeListFragment());

        //buttons
        backbutton = findViewById(R.id.backButton);
        settingsIcon = findViewById(R.id.SettingsIcon);
        addPet = findViewById(R.id.floatPetAddButton);




        //Bottom Navigation
        bottom_navigation = findViewById(R.id.bottom_navigation);


        //Initialize toolbar to change the app bar on the top
        toolbar = findViewById(R.id.AppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        replaceFragment(new HomeListFragment());



        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                //To change title
                TextView toolbarTitle = findViewById(R.id.toolbar_title);

                //Debug statements
                Log.d("NavigationDebug", "Expected nav_home ID: " + R.id.nav_home);
                //Log.d("NavigationDebug", "Expected nav_search ID: " + R.id.nav_search);
                Log.d("NavigationDebug", "Expected nav_Friends ID: " + R.id.nav_Friends);

                //Check which navbarr buttons are clicked
                if (item.getItemId() == R.id.nav_home) {
                    Log.d("NavigationDebug", "Home button clicked");
                    //Gets rid of the title that appears when a navigation tab is clicked
                    getSupportActionBar().setTitle("");
                    toolbarTitle.setText(R.string.app_name);
                    //Display Home Pet List
                    backbutton.setVisibility(GONE);
                    settingsIcon.setVisibility(VISIBLE);
                    addPet.setVisibility(VISIBLE);
                    replaceFragment(new HomeListFragment());
                    return true;

                /*} else if (item.getItemId() == R.id.nav_search) {
                    Log.d("NavigationDebug", "Search button clicked");

                    // Update Toolbar title
                    getSupportActionBar().setTitle("");
                    toolbarTitle.setText(R.string.Search);


                    // Replace the current fragment with the SearchFragment
                    addPet.setVisibility(VISIBLE);
                    backbutton.setVisibility(GONE);
                    settingsIcon.setVisibility(VISIBLE);
                    replaceFragment(new SearchFragment());
                    return true; */

                } else if (item.getItemId() == R.id.nav_Friends) {
                    Log.d("NavigationDebug", "Friends button clicked");
                    // Update Toolbar title
                    getSupportActionBar().setTitle("");
                    toolbarTitle.setText(R.string.Friends);


                    //Transitioning to friend fragment
                    backbutton.setVisibility(GONE);
                    settingsIcon.setVisibility(VISIBLE);
                    addPet.setVisibility(GONE);

                    replaceFragment(new FriendFragment());
                    return true;

                } else {
                    //If something goes wrong with navigation
                    Log.e("NavigationDebug", "Unexpected menu item ID: " + item.getItemId());
                }
                return true;
            }

        });

        //When add pet button is clicked
        addPet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showAddPetDialog();
            }
        });

    }


    //Setting's button --Needs work
    public void onClickPfp(View view) {
        //All buttons gone
        backbutton.setVisibility(VISIBLE);
        settingsIcon.setVisibility(GONE);
        addPet.setVisibility(GONE);

        //Update Toolbar title
        Log.d("Setting Icon", "Clicked: " + view.getId());
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.Settings));
        getSupportActionBar().setTitle("");
        replaceFragment(new SettingsFragment());
    }

    //Back Button In Progress
    public void onClickBckButton(View view) {
        Log.d("ClickDebug", "Back Button Clicked: " + view.getId());
        backbutton.setVisibility(GONE); // Hide the button after clicking
        //Get current Fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.MainFrameContainer);
        Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.MainFrameContainer);

        //Get previous Fragment
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            String previousFragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 2).getName();
            previousFragment = getSupportFragmentManager().findFragmentByTag(previousFragmentTag);
            settingsIcon.setVisibility(VISIBLE);
        }
        getSupportFragmentManager().popBackStack(); // Navigate back if applicable
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.app_name));



    }

    //Check User Login
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "Currently Signed in: " + user.getEmail(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "User not signed in", Toast.LENGTH_SHORT).show();

        }
    }


    //Add Pet Button
    private void showAddPetDialog() {
        backbutton.setVisibility(VISIBLE);
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.dialog_pet_selection_info, null);

        // Find Recycler View inside the popupView
        LinearLayout layout = popupView.findViewById(R.id.linearPetselect);
        RecyclerView recyclerView = popupView.findViewById(R.id.recycler_viewpopup);;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Current available pets
        List<PetInfo> defaultSpecies = new ArrayList<>();
        defaultSpecies.add(new PetInfo("Cat", R.drawable.cat_ic));
        defaultSpecies.add(new PetInfo("Dog", R.drawable.dog_ic));
        defaultSpecies.add(new PetInfo("Bird", R.drawable.birb_ic));
        defaultSpecies.add(new PetInfo("Rabbit", R.drawable.bunny_ic));
        defaultSpecies.add(new PetInfo("Not Listed?", R.drawable.unicat));

        PetAdapter adapter = new PetAdapter(this, defaultSpecies, getSupportFragmentManager(), dialog);
        recyclerView.setAdapter(adapter);

        dialog.setContentView(popupView); // Use the inflated view
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.dialog_pet_selection_bg);
        dialog.show();
        Log.d("ClickDebug", "Pet Selection");

    }


}