package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import Shared.Backend9_Data_Retrieval;

public class Backend9_ViewJob extends AppCompatActivity {
TextView b9_company_name, b9_job_role, b9_total_openings, b9_job_type, b9_job_state, b9_job_city, b9_job_address, b9_job_email, b9_job_info, b9_job_description;
ProgressBar b9_progress_bar;
Button b9_job_request;
String document_id, user_id;
ImageView b9_Back;

FirebaseAuth Auth;
FirebaseFirestore database;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui9_view_job);
        b9_company_name = findViewById(R.id.name_company);
        b9_job_role = findViewById(R.id.name_role);
        b9_total_openings = findViewById(R.id.name_openings);
        b9_job_type = findViewById(R.id.spinner_1);
        b9_job_state = findViewById(R.id.spinner_state);
        b9_job_city = findViewById(R.id.spinner_city);
        b9_job_address = findViewById(R.id.name_address);
        b9_job_email = findViewById(R.id.name_email);
        b9_job_info = findViewById(R.id.name_info);
        b9_progress_bar = findViewById(R.id.ui9_progressBar);
        b9_job_request = findViewById(R.id.name_create_job);
        b9_job_description = findViewById(R.id.name_description);
        b9_Back = findViewById(R.id.imageView4);
        Auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();



        Intent intent = getIntent();
        if(intent!=null){
            document_id = intent.getStringExtra("document_id");
            user_id = intent.getStringExtra("user_id");
        }

        if(document_id.length() ==0 || user_id.length() == 0){
            Toast.makeText(getApplicationContext(), "Some Error Occurred", Toast.LENGTH_SHORT).show();
        }
        else{
            Backend9_Data_Retrieval backend9_data_retrieval = new Backend9_Data_Retrieval(this,Auth , b9_company_name, b9_job_role, b9_total_openings, b9_job_type, b9_job_state, b9_job_city, b9_job_address, b9_job_email, b9_job_info, b9_progress_bar, database, document_id, user_id, b9_job_description);
            backend9_data_retrieval.fetch_and_set_data();
            b9_job_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backend9_data_retrieval.check_user_data();
                }
            });
        }


        b9_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Backend2_Dashboard.class));
                finish();
            }
        });



    }
}