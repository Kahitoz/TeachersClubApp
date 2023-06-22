package Shared.Backend6_Utils;

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

public class Backend6_notification_handler {
    Context context;
    FirebaseAuth auth;
    RecyclerView b6_recycle;
    FirebaseFirestore database;
    ProgressBar progressBar;

    public Backend6_notification_handler(Context context, FirebaseAuth auth, RecyclerView b6_recycle, FirebaseFirestore database, ProgressBar progressBar) {
        this.context = context;
        this.auth = auth;
        this.b6_recycle = b6_recycle;
        this.database = database;
        this.progressBar = progressBar;
    }

    public void get_data(){
        progressBar.setVisibility(View.VISIBLE);
        database.collection("users")
                .document(Objects.requireNonNull(auth.getUid()))
                .collection("notification")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Backend6_notification_GetterSetter> documentList = new ArrayList<>();

                            for (DocumentSnapshot document : task.getResult()) {
                                Backend6_notification_GetterSetter backend5Document = document.toObject(Backend6_notification_GetterSetter.class);
                                documentList.add(backend5Document);
                            }

                            Backend6_notification_Adapter adapter = new Backend6_notification_Adapter(documentList,context);
                            b6_recycle.setAdapter(adapter);
                            b6_recycle.setLayoutManager(new LinearLayoutManager(b6_recycle.getContext()));

                        } else {
                            Toast.makeText(context, "No documents present", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
    }


