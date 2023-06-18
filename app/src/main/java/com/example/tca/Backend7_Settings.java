package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import Shared.Backend7_Update_Handler;

public class Backend7_Settings extends AppCompatActivity {
ImageView b7_Back;
EditText b7_name, b7_phone_number, b7_city, b7_status;
Spinner b7_state;
FirebaseAuth auth;
FirebaseFirestore database;
Button b7_update;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui7_settings);
        b7_Back = findViewById(R.id.ui7_Back);
        b7_name = findViewById(R.id.ui7_name);
        b7_phone_number = findViewById(R.id.ui7_phone);
        b7_city = findViewById(R.id.ui7_city);
        b7_state = findViewById(R.id.spinner_state);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        b7_update = findViewById(R.id.ui7_update);
        b7_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend5_Profile.class));
                finish();
            }
        });

        Backend7_Update_Handler backend7_update_handler = new Backend7_Update_Handler(this, b7_name, b7_phone_number, b7_city, b7_status, b7_state, auth, database);
        backend7_update_handler.add_adapter();
        backend7_update_handler.check_and_update();
        b7_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backend7_update_handler.update_operation();
            }
        });
    }
}