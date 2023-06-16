package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import Shared.Backend3_Form_addJob;

public class Backend3_AddJob extends AppCompatActivity {

    ImageView b3_Back;
    FirebaseAuth auth;
    Spinner stateSpinner, jobTypeSpinner;
    EditText companyEditText, roleEditText, openingsEditText, cityEditText, addressEditText, emailEditText, descriptionEditText, infoEditText;
    Button createJobButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui3_add_job);

        b3_Back = findViewById(R.id.ui3_Back);
        createJobButton = findViewById(R.id.name_create_job);
        companyEditText = findViewById(R.id.name_company);
        roleEditText = findViewById(R.id.name_role);
        openingsEditText = findViewById(R.id.name_openings);
        cityEditText = findViewById(R.id.name_add_city);
        addressEditText = findViewById(R.id.name_address);
        emailEditText = findViewById(R.id.name_email);
        descriptionEditText = findViewById(R.id.name_description);
        infoEditText = findViewById(R.id.name_info);
        stateSpinner = findViewById(R.id.spinner_state);
        jobTypeSpinner = findViewById(R.id.name_job_type);

        auth = FirebaseAuth.getInstance();

        Backend3_Form_addJob backend3_formattedJob = new Backend3_Form_addJob(stateSpinner, jobTypeSpinner, companyEditText, roleEditText,
                openingsEditText, cityEditText, addressEditText, emailEditText, descriptionEditText, infoEditText, this);

        String[] indianStates = {"Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, indianStates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);

        String[] jobTypes = {"Intern", "Part Time", "Full Time"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jobTypes);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobTypeSpinner.setAdapter(adapter1);

        b3_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend2_Dashboard.class));
                finish();
            }
        });

        createJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backend3_formattedJob.validate_form()) {
                    backend3_formattedJob.upload_job_data();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser == null) {
                // User has not signed in, redirect to login activity
                Intent intent = new Intent(this, Backend4_LoginMessage.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
