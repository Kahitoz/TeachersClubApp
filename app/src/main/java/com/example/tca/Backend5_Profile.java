package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tca.Backend4_LoginMessage;
import com.example.tca.Backend7_Settings;
import com.example.tca.Backend8_AddDocument;
import com.example.tca.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import Shared.Backend5_utils.Backend5_bio_handler;
import Shared.Backend5_utils.Backend5_documents.Backend5_document_handler;
import Shared.Backend5_utils.Backend5_jobsAdded.Backend5_jobAdded_handler;
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

        Backend5_bio_handler backend5_bio_handler = new Backend5_bio_handler(this, Auth, database, b5_name, b5_bio);
        Backend5_document_handler backend5_document_handler = new Backend5_document_handler(this, Auth, database, b5_recycler_view, b5_progress_bar);
        Backend5_jobAdded_handler backend5_jobAdded_handler = new Backend5_jobAdded_handler(this, Auth, database, b5_recycler_view, b5_progress_bar);

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

        b5_job_posted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backend5_jobAdded_handler.get_job();
            }
        });

        // Check if the user is logged in
        FirebaseUser currentUser = Auth.getCurrentUser();
        if (currentUser != null) {
            backend5_bio_handler.set_profile();
            // Additional code to execute when the user is logged in
        } else {
            // User is not logged in, handle the situation (e.g., show a login screen)
            Intent intent = new Intent(this, Backend4_LoginMessage.class);
            startActivity(intent);
            finish();
        }
    }
}
