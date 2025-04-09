package com.petpawology.petwhisper;
import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.petpawology.petwhisper.databinding.ActivityMainBinding;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyData myData;
    private Intent intent;

    ActivityMainBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.LightPurp));


        // Apply window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeListFragment());


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //Initialize toolbar to change the app bar on the top
                Toolbar toolbar = findViewById(R.id.AppBar);
                setSupportActionBar(toolbar);
                //To change title
                TextView toolbarTitle = findViewById(R.id.toolbar_title);

                //Debug statements
                Log.d("NavigationDebug", "Expected nav_home ID: " + R.id.nav_home);
                Log.d("NavigationDebug", "Expected nav_search ID: " + R.id.nav_search);
                Log.d("NavigationDebug", "Expected nav_Friends ID: " + R.id.nav_Friends);

                //Check which navbarr buttons are clicked
                if (item.getItemId() == R.id.nav_home) {
                    Log.d("NavigationDebug", "Home button clicked");
                    //Gets rid of the title that appears when a navigation tab is clicked
                    getSupportActionBar().setTitle("");
                    toolbarTitle.setText("PetWhisper");
                    //Display Home Pet List
                    replaceFragment(new HomeListFragment());
                    return true;

                } else if (item.getItemId() == R.id.nav_search) {
                    Log.d("NavigationDebug", "Search button clicked");

                    // Update Toolbar title
                    getSupportActionBar().setTitle("");
                    toolbarTitle.setText("Search");


                    // Replace the current fragment with the SearchFragment
                    replaceFragment(new SearchFragment());
                    return true;

                } else if (item.getItemId() == R.id.nav_Friends) {
                    Log.d("NavigationDebug", "Friends button clicked");

                    // Update Toolbar title
                    getSupportActionBar().setTitle("");
                    toolbarTitle.setText("Friends");

                    //Transitioning to friend fragment
                    replaceFragment(new FriendFragment());

                    return true;

                } else {
                    //If something goes wrong
                    Log.e("NavigationDebug", "Unexpected menu item ID: " + item.getItemId());
                }
                return true;
            }
        });

    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.MainFrameContainer, fragment).commit();
    }
}