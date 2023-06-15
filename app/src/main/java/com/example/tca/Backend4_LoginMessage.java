package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Backend4_LoginMessage extends AppCompatActivity {
ImageView b4_Back;
Button b4_SignIn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui4_login_message);

        b4_Back = findViewById(R.id.ui4_Back);
        b4_SignIn = findViewById(R.id.ui4_SignIn);

        b4_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend1_Login.class));
                finish();
            }
        });
        b4_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend2_Dashboard.class));
                finish();
            }
        });


    }


}