package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import Shared.Backeng6_Utils.Backend6_notification_handler;
import Shared.NavbarFunctionality;

public class Backend6_Notification extends AppCompatActivity {
    FirebaseAuth auth;
    RecyclerView b6_recycle;
    FirebaseFirestore database;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui6_notification);
        NavbarFunctionality navbar = new NavbarFunctionality(this);
        navbar.Notification_handler();
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        b6_recycle = findViewById(R.id.notify_recycle);
        progressBar = findViewById(R.id.notify_progress);
        progressBar.setVisibility(View.INVISIBLE);
        Backend6_notification_handler backend6_notification_handler = new Backend6_notification_handler(this,auth,b6_recycle,database,progressBar);
        backend6_notification_handler.get_data();


    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            FirebaseUser currentUser = auth.getCurrentUser();
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