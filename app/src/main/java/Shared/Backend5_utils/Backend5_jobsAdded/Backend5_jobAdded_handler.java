package Shared.Backend5_utils.Backend5_jobsAdded;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Shared.Backend5_utils.Backend5_jobsAdded.Backend5_get_set_jobAdded;
import Shared.Backend5_utils.Backend5_jobsAdded.Backend5_jobAdded_Adapter;

public class Backend5_jobAdded_handler {
    private final Context context;
    private final FirebaseAuth auth;
    private final FirebaseFirestore database;
    private final RecyclerView b5_recyclerView;
    private final ProgressBar b5_progress_bar;

    public Backend5_jobAdded_handler(Context context, FirebaseAuth auth, FirebaseFirestore database, RecyclerView b5_recyclerView, ProgressBar b5_progress_bar) {
        this.context = context;
        this.auth = auth;
        this.database = database;
        this.b5_recyclerView = b5_recyclerView;
        this.b5_progress_bar = b5_progress_bar;
    }
    public void get_job(){
        b5_progress_bar.setVisibility(View.VISIBLE);
        database.collection("users")
                .document(Objects.requireNonNull(auth.getUid()))
                .collection("jobPosted")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Backend5_get_set_jobAdded> documentList = new ArrayList<>();

                            for (DocumentSnapshot document : task.getResult()) {
                                Backend5_get_set_jobAdded backend5Document = document.toObject(Backend5_get_set_jobAdded.class);
                                documentList.add(backend5Document);
                            }

                            Backend5_jobAdded_Adapter adapter = new Backend5_jobAdded_Adapter(documentList, context);
                            b5_recyclerView.setAdapter(adapter);
                            b5_recyclerView.setLayoutManager(new LinearLayoutManager(b5_recyclerView.getContext()));
                        } else {
                            Toast.makeText(context, "No documents present", Toast.LENGTH_SHORT).show();
                        }
                        b5_progress_bar.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                        b5_progress_bar.setVisibility(View.GONE);
                    }
                });
    }
}
