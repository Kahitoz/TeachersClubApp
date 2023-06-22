package Shared.Backend10_Utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
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

import Shared.Backend6_Utils.Backend6_notification_Adapter;
import Shared.Backend6_Utils.Backend6_notification_GetterSetter;
import kotlin.reflect.KVisibility;

public class Backend10_jobApplicants_handler {
    Context context;
    FirebaseAuth auth;
    FirebaseFirestore database;
    String job_id;
    ImageView b10_back;
    RecyclerView b10_recycle;
    ProgressBar b10_progress;

    public Backend10_jobApplicants_handler(Context context, FirebaseAuth auth, FirebaseFirestore database, String job_id, ImageView b10_back, RecyclerView b10_recycle, ProgressBar b10_progress) {
        this.context = context;
        this.auth = auth;
        this.database = database;
        this.job_id = job_id;
        this.b10_back = b10_back;
        this.b10_recycle = b10_recycle;
        this.b10_progress = b10_progress;
    }

    public void get_Data(){
        b10_progress.setVisibility(View.VISIBLE);
        database.collection("users").document(Objects.requireNonNull(auth.getUid())).collection("jobPosted").document(job_id.trim()).collection("Applied_Job").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Backend10_jobApplicants_getterSetter> documentList = new ArrayList<>();

                    for (DocumentSnapshot document : task.getResult()) {
                        Backend10_jobApplicants_getterSetter backend5Document = document.toObject(Backend10_jobApplicants_getterSetter.class);
                        documentList.add(backend5Document);
                    }

                    Backend10_jobApplicants_adapter adapter = new Backend10_jobApplicants_adapter(context,documentList);
                    b10_recycle.setAdapter(adapter);
                    b10_recycle.setLayoutManager(new LinearLayoutManager(b10_recycle.getContext()));

                } else {
                    Toast.makeText(context, "No documents present", Toast.LENGTH_SHORT).show();
                }
                b10_progress.setVisibility(View.GONE);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                b10_progress.setVisibility(View.GONE);
            }
        });
    }
}
