package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import Shared.NavbarFunctionality;

public class Backend6_Notification extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui6_notification);
        NavbarFunctionality navbar = new NavbarFunctionality(this);
        navbar.Notification_handler();
    }
}