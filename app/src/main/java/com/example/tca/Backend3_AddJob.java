package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Backend3_AddJob extends AppCompatActivity {

    ImageView b3_Back;
    FirebaseAuth Auth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui3_add_job);
        b3_Back = findViewById(R.id.ui3_Back);

        Auth = FirebaseAuth.getInstance();
        b3_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Backend2_Dashboard.class));
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        try {
            FirebaseUser currentUser = Auth.getCurrentUser();
            if (currentUser == null) {
                // User is not signed in, redirect to login activity
                Intent intent = new Intent(this, Backend4_LoginMessage.class);
                startActivity(intent);
                finish();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    }
