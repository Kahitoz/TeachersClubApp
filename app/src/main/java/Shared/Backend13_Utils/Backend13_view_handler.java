package Shared.Backend13_Utils;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Shared.Backend5_utils.Backend5_documents.Backend5_document_Adapter;
import Shared.Backend5_utils.Backend5_documents.Backend5_get_set_document;

public class Backend13_view_handler {
    Context context;
    String get_id;
    FirebaseAuth auth;
    FirebaseFirestore database;
    TextView b13_bio, b13_info, b13_name;
    ProgressBar b13_progress;
    RecyclerView b13_recycle;

    public Backend13_view_handler(Context context, String get_id, FirebaseAuth auth, FirebaseFirestore database, TextView b13_name, TextView b13_info, ProgressBar b13_progress, RecyclerView b13_recycle, TextView b13_bio) {
        this.context = context;
        this.get_id = get_id;
        this.auth = auth;
        this.database = database;
        this.b13_name = b13_name;
        this.b13_info = b13_info;
        this.b13_progress = b13_progress;
        this.b13_recycle = b13_recycle;
        this.b13_bio = b13_bio;
    }

    public void update_bio(){
        database.collection("users").document(get_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();
                    if(snapshot!=null){
                        String name = snapshot.getString("name");
                        String bio = snapshot.getString("status");
                        String email = snapshot.getString("email");
                        String phone = snapshot.getString("phone");
                        String state = snapshot.getString("state");
                        String city = snapshot.getString("city");

                        String info = email+" "+phone+" "+state+","+city;
                        b13_name.setText(name);
                        b13_bio.setText(bio);
                        b13_info.setText(info);


                    }
                }
            }
        });
    }

    public void update_document(){
        b13_progress.setVisibility(View.VISIBLE);
        database.collection("users")
                .document(get_id)
                .collection("Documents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Backend5_get_set_document> documentList = new ArrayList<>();

                            for (DocumentSnapshot document : task.getResult()) {
                                Backend5_get_set_document backend5Document = document.toObject(Backend5_get_set_document.class);
                                documentList.add(backend5Document);
                            }

                            Backend13_view_adapter adapter = new Backend13_view_adapter(documentList, context);
                            b13_recycle.setAdapter(adapter);
                            b13_recycle.setLayoutManager(new LinearLayoutManager(b13_recycle.getContext()));
                        } else {
                            Toast.makeText(context, "No documents present", Toast.LENGTH_SHORT).show();
                        }
                       b13_progress.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                        b13_progress.setVisibility(View.GONE);
                    }
                });
    }
}
