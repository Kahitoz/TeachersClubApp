package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Backend8_AddDocument extends AppCompatActivity {
ImageView b8_Back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui8_add_document);
        b8_Back = findViewById(R.id.ui8_Back);
        b8_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend5_Profile.class));
                finish();
            }
        });
    }
}