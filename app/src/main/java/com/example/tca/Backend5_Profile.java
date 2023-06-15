package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import Shared.NavbarFunctionality;

public class Backend5_Profile extends AppCompatActivity {
ImageView b5_settings;
FrameLayout b5_add_document;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui5_profile);
        NavbarFunctionality navbar = new NavbarFunctionality(this);
        navbar.Profile_handler();
        b5_settings = findViewById(R.id.ui5_settings);
        b5_add_document = findViewById(R.id.ui5_add_document);
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


    }
}