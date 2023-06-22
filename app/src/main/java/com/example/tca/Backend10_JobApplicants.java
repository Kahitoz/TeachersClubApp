package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import Shared.Backend10_Utils.Backend10_jobApplicants_handler;

public class Backend10_JobApplicants extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;
    String job_id;
    ImageView b10_back;
    RecyclerView b10_recycle;
    ProgressBar b10_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui10_job_applicants);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        b10_back = findViewById(R.id.ui10_back);
        b10_recycle = findViewById(R.id.ui10_recycle);
        b10_progress = findViewById(R.id.ui10_progress);
        Intent intent = getIntent();
        job_id = intent.getStringExtra("job_id");
        if(job_id != null){
            Backend10_jobApplicants_handler backend10_jobApplicants_handler = new Backend10_jobApplicants_handler(this, auth, database, job_id, b10_back, b10_recycle, b10_progress);
            backend10_jobApplicants_handler.get_Data();
        }else{
            Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
        }

        b10_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend5_Profile.class));
                finish();
            }
        });
    }
}