package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Backend3_AddJob extends AppCompatActivity {

    ImageView b3_Back;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    Spinner stateSpinner, jobTypeSpinner;
    EditText companyEditText, roleEditText, openingsEditText, cityEditText, addressEditText, emailEditText, descriptionEditText, infoEditText;
    Button createJobButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui3_add_job);
        b3_Back = findViewById(R.id.ui3_Back);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        stateSpinner = findViewById(R.id.spinner_state);
        jobTypeSpinner = findViewById(R.id.name_job_type);
        companyEditText = findViewById(R.id.name_company);
        roleEditText = findViewById(R.id.name_role);
        openingsEditText = findViewById(R.id.name_openings);
        cityEditText = findViewById(R.id.name_add_city);
        addressEditText = findViewById(R.id.name_address);
        emailEditText = findViewById(R.id.name_email);
        descriptionEditText = findViewById(R.id.name_description);
        infoEditText = findViewById(R.id.name_info);
        createJobButton = findViewById(R.id.name_create_job);

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
                if (validateForm()) {
                    uploadJobData();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean isValid = true;

        if (companyEditText.getText().toString().isEmpty()) {
            companyEditText.setError("Please enter company name");
            isValid = false;
        }

        if (roleEditText.getText().toString().isEmpty()) {
            roleEditText.setError("Please enter job role");
            isValid = false;
        }

        if (openingsEditText.getText().toString().isEmpty()) {
            openingsEditText.setError("Please enter number of openings");
            isValid = false;
        }

        if (cityEditText.getText().toString().isEmpty()) {
            cityEditText.setError("Please enter city");
            isValid = false;
        }

        if (addressEditText.getText().toString().isEmpty()) {
            addressEditText.setError("Please enter address");
            isValid = false;
        }

        if (emailEditText.getText().toString().isEmpty()) {
            emailEditText.setError("Please enter email");
            isValid = false;
        }

        if (descriptionEditText.getText().toString().isEmpty()) {
            descriptionEditText.setError("Please enter job description");
            isValid = false;
        }

        if (infoEditText.getText().toString().isEmpty()) {
            infoEditText.setError("Please enter additional info");
            isValid = false;
        }

        return isValid;
    }

    private void uploadJobData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            String state = stateSpinner.getSelectedItem().toString();
            String jobType = jobTypeSpinner.getSelectedItem().toString();
            String company = companyEditText.getText().toString();
            String role = roleEditText.getText().toString();
            int openings = Integer.parseInt(openingsEditText.getText().toString());
            String city = cityEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String info = infoEditText.getText().toString();

            // Create a new job map
            Map<String, Object> jobMap = new HashMap<>();
            jobMap.put("state", state);
            jobMap.put("jobType", jobType);
            jobMap.put("company", company);
            jobMap.put("role", role);
            jobMap.put("openings", openings);
            jobMap.put("city", city);
            jobMap.put("address", address);
            jobMap.put("email", email);
            jobMap.put("description", description);
            jobMap.put("info", info);

            // Upload job data to Firestore
            CollectionReference jobPostedRef = firestore.collection("users").document(userId).collection("jobPosted");
            jobPostedRef.add(jobMap)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(Backend3_AddJob.this, "Job created successfully", Toast.LENGTH_SHORT).show();
                        clearForm();
                    })
                    .addOnFailureListener(e -> Toast.makeText(Backend3_AddJob.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void clearForm() {
        stateSpinner.setSelection(0);
        jobTypeSpinner.setSelection(0);
        companyEditText.setText("");
        roleEditText.setText("");
        openingsEditText.setText("");
        cityEditText.setText("");
        addressEditText.setText("");
        emailEditText.setText("");
        descriptionEditText.setText("");
        infoEditText.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser == null) {
                // User is not signed in, redirect to login activity
                Intent intent = new Intent(this, Backend4_LoginMessage.class);
                startActivity(intent);
                finish();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
