package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import Shared.NavbarFunctionality;

public class Backend5_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui5_profile);
        NavbarFunctionality navbar = new NavbarFunctionality(this);
        navbar.Profile_handler();
    }
}