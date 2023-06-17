package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import Shared.Backen2_Utils.Backend2_Adapter;
import Shared.Backen2_Utils.Backend2_DataRetriever;
import Shared.NavbarFunctionality;

public class Backend2_Dashboard extends AppCompatActivity {
    FrameLayout add_job;
    ProgressBar b2_progress;
    RecyclerView b2_recycle;

    FirebaseFirestore database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui2_dashboard);
        NavbarFunctionality navbar = new NavbarFunctionality(this);
        navbar.Home_handler();

        b2_recycle = findViewById(R.id.ui2_recycle);
        b2_progress = findViewById(R.id.ui2_progress);

        add_job = findViewById(R.id.add_job);
        add_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend3_AddJob.class));
            }
        });

        database = FirebaseFirestore.getInstance();

        Backend2_DataRetriever backend2_dataRetriever = new Backend2_DataRetriever(b2_recycle, b2_progress, database);
        backend2_dataRetriever.retrieve_open_job_posts();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        b2_recycle.setLayoutManager(layoutManager);
        Backend2_Adapter adapter = new Backend2_Adapter(new ArrayList<>());
        b2_recycle.setAdapter(adapter);
    }
}
