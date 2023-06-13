package com.example.tca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Backend1_Login extends AppCompatActivity {

    EditText b1_email, b1_password;
    TextView b1_forgot_password, b1_skip, b1_login;
    FirebaseAuth auth;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui1_login);
        b1_email = findViewById(R.id.ui1_email);
        b1_password = findViewById(R.id.ui1_password);
        b1_login = findViewById(R.id.ui1_login_signup);
        b1_skip = findViewById(R.id.ui1_skip);
        auth = FirebaseAuth.getInstance();

        b1_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = b1_email.getText().toString().trim();
                String password = b1_password.getText().toString().trim();

                // Perform Firebase authentication
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Backend1_Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Authentication successful
                                    Intent intent = new Intent(Backend1_Login.this, Backend0_CheckSignIn.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // User does not exist, create a new user
                                    auth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(Backend1_Login.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        // User created successfully
                                                        Intent intent = new Intent(Backend1_Login.this, Backend0_CheckSignIn.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        // Failed to create user
                                                        Toast.makeText(Backend1_Login.this, "Authentication failed.",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        });
        b1_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend2_Dashboard.class));
                finish();
            }
        });
    }
}