package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class Backend13_ViewProfile extends AppCompatActivity {
    String get_id;
    FirebaseAuth auth;
    FirebaseFirestore database;
    TextView b13_bio, b13_info;
    ProgressBar b13_progress;
    RecyclerView b13_recycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui13_view_profile);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        b13_bio = findViewById(R.id.profile_name);
        b13_info = findViewById(R.id.ui13_info);
        b13_progress = findViewById(R.id.ui13_progress);
        b13_recycle = findViewById(R.id.ui13_recycle);

    }
}