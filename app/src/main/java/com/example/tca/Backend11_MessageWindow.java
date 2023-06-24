package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import Shared.Backend11_Utils.Applicant.Backend11_Applicant_chatHandler;

public class Backend11_MessageWindow extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;
    ImageView b11_back, b11_send;
    ProgressBar b11_progress;
    EditText b11_message;
    RecyclerView b11_recycle;

    String applicant_id, job_id, hire_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui11_message_window);
        auth  = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        b11_back = findViewById(R.id.ui10_back);
        b11_send = findViewById(R.id.ui11_send);
        b11_progress = findViewById(R.id.ui11_progress);
        b11_message = findViewById(R.id.ui11_input);
        b11_recycle = findViewById(R.id.ui11_message);

        Intent intent = getIntent();
        applicant_id = intent.getStringExtra("applicant_id");
        job_id = intent.getStringExtra("job_id");
        hire_id = intent.getStringExtra("hire_id");

        if(applicant_id!=null&&job_id!=null&&hire_id!=null){
            Backend11_Applicant_chatHandler handle = new Backend11_Applicant_chatHandler(this, auth, database,b11_back,
                    b11_send, b11_progress, b11_message,b11_recycle, applicant_id, job_id, hire_id);
            handle.fetch_chat();
            b11_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Backend5_Profile.class));
                    finish();
                }
            });
            b11_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handle.send_chat();
                    handle.fetch_chat();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Req null", Toast.LENGTH_SHORT).show();
        }

    }
}