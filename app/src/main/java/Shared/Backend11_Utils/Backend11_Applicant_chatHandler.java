package Shared.Backend11_Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tca.Backend5_Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import Shared.Get_Date;

public class Backend11_Applicant_chatHandler {
    Context context;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ImageView b11_back, b11_send;
    ProgressBar b11_progress;
    EditText b11_message;
    String applicant_id, job_id, hire_id;

    public Backend11_Applicant_chatHandler(Context context, FirebaseAuth auth,
                                           FirebaseFirestore database, ImageView b11_back,
                                           ImageView b11_send, ProgressBar b11_progress,
                                           EditText b11_message, String applicant_id,
                                           String job_id, String hire_id) {
        this.context = context;
        this.auth = auth;
        this.database = database;
        this.b11_back = b11_back;
        this.b11_send = b11_send;
        this.b11_progress = b11_progress;
        this.b11_message = b11_message;
        this.applicant_id = applicant_id;
        this.job_id = job_id;
        this.hire_id = hire_id;
    }
    public void basic_function(final Activity activity){
        b11_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Backend5_Profile.class));
                activity.finish();
            }
        });
    }
    public void send_chat() {
                Toast.makeText(context,"sending", Toast.LENGTH_SHORT).show();
                Get_Date get_date = new Get_Date();
                String get_message = b11_message.getText().toString();
                String date = get_date.getCurrentDate();

                // Get the user's name asynchronously
                get_name().thenAccept(name -> {
                    if (get_message.length() == 0) {
                        b11_message.setError("Cannot be blank");
                    } else {
                        Map<String, Object> chat = new HashMap<>();
                        chat.put("date", date);
                        chat.put("name", name);
                        chat.put("message", get_message);

                        database.collection("users").document(hire_id)
                                .collection("jobPosted").document(job_id)
                                .collection("Applied_Job").document(applicant_id)
                                .collection("chat").document().set(chat)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show();
                                        b11_message.setText("");
                                    }
                                });
                    }
                }).exceptionally(ex -> {
                    // Handle any errors retrieving the name
                    ex.printStackTrace();
                    return null;
                });
            }




    public CompletableFuture<String> get_name() {
        CompletableFuture<String> future = new CompletableFuture<>();

        database.collection("users")
                .document(Objects.requireNonNull(auth.getUid()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot snapshot = task.getResult();
                        if (snapshot != null) {
                            String name = snapshot.getString("name");
                            future.complete(name); // Complete the CompletableFuture with the retrieved name
                        } else {
                            future.completeExceptionally(new Exception("User document not found"));
                        }
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }
}
