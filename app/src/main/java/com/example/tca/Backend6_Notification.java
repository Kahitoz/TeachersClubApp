package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Shared.NavbarFunctionality;

public class Backend6_Notification extends AppCompatActivity {
    FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui6_notification);
        NavbarFunctionality navbar = new NavbarFunctionality(this);
        navbar.Notification_handler();
        Auth = FirebaseAuth.getInstance();
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