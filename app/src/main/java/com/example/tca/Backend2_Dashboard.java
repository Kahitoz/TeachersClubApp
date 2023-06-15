package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import Shared.NavbarFunctionality;

public class Backend2_Dashboard extends AppCompatActivity {
    FrameLayout add_job;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui2_dashboard);
        NavbarFunctionality navbar = new NavbarFunctionality(this);
        navbar.Home_handler();

        add_job = findViewById(R.id.add_job);
        add_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend3_AddJob.class));
            }
        });
    }
}