package Shared.Backend5_utils;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Backend5_bio_handler {
    Context context;
    FirebaseAuth auth;
    FirebaseFirestore database;
    TextView b5_name, b5_bio;

    public Backend5_bio_handler(Context context, FirebaseAuth auth, FirebaseFirestore database, TextView b5_name, TextView b5_bio) {
        this.context = context;
        this.auth = auth;
        this.database = database;
        this.b5_name = b5_name;
        this.b5_bio = b5_bio;
    }

    public void set_profile(){

        database.collection("users").document((Objects.requireNonNull(auth.getUid()))).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot!=null){
                        String name = documentSnapshot.getString("name");
                        String bio = documentSnapshot.getString("status");
                        b5_name.setText(name);
                        b5_bio.setText(bio);
                    }
                }
            }
        });
    }




}
