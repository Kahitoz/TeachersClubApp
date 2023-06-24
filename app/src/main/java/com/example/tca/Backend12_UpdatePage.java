package com.example.tca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Backend12_UpdatePage extends AppCompatActivity {
    final String App_Version = "1.0.0";
    FirebaseFirestore database;
    ImageView b12_forward;
    ProgressBar b12_progress;
    TextView b12_text;
    Button b12_update;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui12_update_page);
        database = FirebaseFirestore.getInstance();
        b12_progress = findViewById(R.id.ui12_progress);
        b12_forward = findViewById(R.id.ui12_forward);
        b12_text = findViewById(R.id.ui12_message);
        b12_update = findViewById(R.id.ui12_update);
        database.collection("GlobalUpdate").document("message").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    b12_progress.setVisibility(View.INVISIBLE);
                    DocumentSnapshot snapshot = task.getResult();
                    if(snapshot!=null){
                        String version = snapshot.getString("version");
                        String message = snapshot.getString("message");
                        String link = snapshot.getString("link");
                        String type = snapshot.getString("type");
                        if(App_Version.equals(version)&& !Objects.equals(type, "urgent")){
                            startActivity(new Intent(getApplicationContext(), Backend5_Profile.class));
                        }else {
                            assert type != null;
                            if(type.equals("urgent")&&!App_Version.equals(version)){
                                b12_forward.setVisibility(View.GONE);
                                b12_text.setText(message);
                                b12_update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                        startActivity(browserIntent);
                                    }
                                });

                            }else {
                                b12_text.setText(message);
                                b12_update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                        startActivity(browserIntent);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });



    }
}