package com.petpawology.petwhisper;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterAcc extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Input fields
        TextInputEditText editTextEmail, editTextPassword, editTextName, editTextUsername;

        //buttons
        Button buttonRegister;
        ImageButton bckbutton;
        TextView loginAlrExists;

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_acc);


        /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         */

        setContentView(R.layout.activity_register_acc);
        editTextEmail = findViewById(R.id.emailEnter);
        editTextPassword = findViewById(R.id.passwordEnt);
        editTextName = findViewById(R.id.nameEnter);
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
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email, password, name, username;
                email = String.valueOf(editTextEmail);
                password = String.valueOf(editTextPassword);
                name = String.valueOf(editTextName);
                username = String.valueOf(editTextUsername);

                //Checking if the fields are empty
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterAcc.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterAcc.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(RegisterAcc.this, "Authentication success.", Toast.LENGTH_SHORT).show();

                                    //updateUI(user); <- For later [maybe]
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterAcc.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                    //updateUI(null); For later [maybe]
                                }
                            }
                        });



            }
        });
    }
}