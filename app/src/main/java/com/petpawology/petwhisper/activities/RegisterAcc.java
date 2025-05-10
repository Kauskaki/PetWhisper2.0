package com.petpawology.petwhisper.activities;

import static android.app.ProgressDialog.show;
import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.petpawology.petwhisper.AccountController;
import com.petpawology.petwhisper.userData;
import com.petpawology.petwhisper.R;

import java.util.Map;

public class RegisterAcc extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private userData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Input fields
        TextInputEditText editTextEmail, editTextPassword, editTextUsername;

        //buttons
        Button buttonRegister;
        ImageButton bckbutton;
        TextView loginAlrExists;

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_acc);

        //Enter User Information
        setContentView(R.layout.activity_register_acc);
        editTextEmail = findViewById(R.id.emailEnter);
        editTextPassword = findViewById(R.id.passwordEnt);
        editTextUsername = findViewById(R.id.usernameEnt);
        buttonRegister = findViewById(R.id.createAccBtn);
        bckbutton = findViewById(R.id.bckbutton);
        loginAlrExists = findViewById(R.id.loginAlrExists);

        //Button if Account Already Exists.
        loginAlrExists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


        //Registering User Once button is pressed
        buttonRegister.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String username = editTextUsername.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(RegisterAcc.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(RegisterAcc.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(RegisterAcc.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();

                                // Initialize the singleton controller


                                // Save user info to Firestore
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("users").document(userId)
                                        .set(Map.of(
                                                "email", email,
                                                "username", username,
                                                "userId", userId
                                        ))
                                        .addOnSuccessListener(unused -> {
                                            AccountController.getInstance().init(userId, () -> {
                                                // Called once pets + friends data is fully loaded (even if empty)
                                                Toast.makeText(RegisterAcc.this, "Data loaded!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterAcc.this, MainActivity.class));
                                                finish();
                                            });
                                        });
                            }
                        } else {
                            Toast.makeText(RegisterAcc.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }
}