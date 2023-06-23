package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import Shared.Backend13_Utils.Backend13_view_handler;

public class Backend13_ViewProfile extends AppCompatActivity {
    String get_id;
    FirebaseAuth auth;
    FirebaseFirestore database;
    TextView b13_bio, b13_info, b13_name;
    ProgressBar b13_progress;
    RecyclerView b13_recycle;
    ImageView b13_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui13_view_profile);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        b13_name= findViewById(R.id.profile_name);
        b13_info = findViewById(R.id.ui13_info);
        b13_progress = findViewById(R.id.ui13_progress);
        b13_recycle = findViewById(R.id.ui13_recycle);
        b13_bio = findViewById(R.id.ui5_bio);
        b13_back = findViewById(R.id.imageView);

        Intent intent = getIntent();
        get_id=intent.getStringExtra("uid");

        if(get_id!=null){
            Backend13_view_handler handler = new Backend13_view_handler(this, get_id, auth, database, b13_name, b13_info, b13_progress, b13_recycle, b13_bio);
            handler.update_bio();
            handler.update_document();

        }else{
            Toast.makeText(this, "Try again later", Toast.LENGTH_SHORT).show();
        }

        b13_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend5_Profile.class));
                finish();
            }
        });

    }
}