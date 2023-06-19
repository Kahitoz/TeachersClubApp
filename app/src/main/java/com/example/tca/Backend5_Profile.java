package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import Shared.Backend5_bio_handler;
import Shared.Backend5_document_handler;
import Shared.NavbarFunctionality;

public class Backend5_Profile extends AppCompatActivity {
ImageView b5_settings;
FrameLayout b5_add_document;
TextView b5_name, b5_bio, b5_documents, b5_job_posted, b5_job_applied;
ProgressBar b5_progress_bar;
RecyclerView b5_recycler_view;
FirebaseFirestore database;
FirebaseAuth Auth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui5_profile);
        NavbarFunctionality navbar = new NavbarFunctionality(this);
        navbar.Profile_handler();
        b5_settings = findViewById(R.id.ui5_settings);
        b5_add_document = findViewById(R.id.ui5_add_document);
        b5_name = findViewById(R.id.profile_name);
        b5_bio = findViewById(R.id.ui5_bio);

        b5_documents = findViewById(R.id.ui5_documents);
        b5_job_posted = findViewById(R.id.ui5_job_posted);
        b5_job_applied = findViewById(R.id.ui5_job_applied);

        b5_recycler_view = findViewById(R.id.recyclerView);
        b5_progress_bar = findViewById(R.id.profile_progress);

        Auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        Backend5_bio_handler backend5_bio_handler = new Backend5_bio_handler(this,Auth,database,b5_name,b5_bio);
        backend5_bio_handler.set_profile();
        Backend5_document_handler backend5_document_handler = new Backend5_document_handler(this, Auth, database, b5_recycler_view, b5_progress_bar);
        backend5_document_handler.get_data();

        b5_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend7_Settings.class));
            }
        });

        b5_add_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend8_AddDocument.class));
            }
        });


        b5_documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backend5_document_handler.get_data();
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