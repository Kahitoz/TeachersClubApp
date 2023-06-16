package Shared;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Backend2_DataRetriever {
    private final RecyclerView b2_recycle;
    private final ProgressBar b2_progress;
    private final FirebaseFirestore database;

    public Backend2_DataRetriever(RecyclerView b2_recycle, ProgressBar b2_progress, FirebaseFirestore database) {
        this.b2_recycle = b2_recycle;
        this.b2_progress = b2_progress;
        this.database = database;
    }

    public void retrieve_open_job_posts() {
        database.collectionGroup("jobPosted").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        // Process the job posts
                        List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                        List<Backend2_GetAndSet> dataList = new ArrayList<>();
                        for (DocumentSnapshot document : documentSnapshots) {
                            String status = document.getString("status");
                            if (status != null && status.equals("open")) {
                                Backend2_GetAndSet item = document.toObject(Backend2_GetAndSet.class);
                                dataList.add(item);
                            }
                        }
                        Backend2_Adapter adapter = new Backend2_Adapter(dataList);
                        b2_recycle.setAdapter(adapter);
                    } else {
                        Toast.makeText(b2_recycle.getContext(), "No new jobs available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Log the error message
                    Exception exception = task.getException();
                    if (exception != null) {
                        Log.e("DataRetriever", "Failed to retrieve job posts: " + exception.getMessage());
                    }
                    Toast.makeText(b2_recycle.getContext(), "Failed to retrieve job posts", Toast.LENGTH_SHORT).show();
                }
                b2_progress.setVisibility(View.GONE);
            }
        });
    }


}
