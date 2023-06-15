package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import Shared.NavbarFunctionality;

public class Backend2_Dashboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui2_dashboard);
        NavbarFunctionality navbar = new NavbarFunctionality(this);
        navbar.Home_handler();
    }
}