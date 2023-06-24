package Shared;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tca.Backend5_Profile;
import com.example.tca.Backend7_Settings;
import com.example.tca.Backend9_ViewJob;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Objects;

public class Backend9_Data_Retrieval {
    private final TextView b9_company_name, b9_job_role, b9_total_openings, b9_job_type, b9_job_state, b9_job_city, b9_job_address, b9_job_email, b9_job_info, b9_job_description;
    private final ProgressBar b9_progress_bar;
    private final FirebaseFirestore database;

    private final String document_id, user_id;
    private final Context context;
    private final FirebaseAuth auth;

    public Backend9_Data_Retrieval(Context context, FirebaseAuth auth, TextView b9_company_name, TextView b9_job_role, TextView b9_total_openings, TextView b9_job_type, TextView b9_job_state, TextView b9_job_city, TextView b9_job_address, TextView b9_job_email, TextView b9_job_info, ProgressBar b9_progress_bar, FirebaseFirestore database, String document_id, String user_id, TextView b9_job_description) {
        this.b9_company_name = b9_company_name;
        this.b9_job_role = b9_job_role;
        this.b9_total_openings = b9_total_openings;
        this.b9_job_type = b9_job_type;
        this.b9_job_state = b9_job_state;
        this.b9_job_city = b9_job_city;
        this.b9_job_address = b9_job_address;
        this.b9_job_email = b9_job_email;
        this.b9_job_info = b9_job_info;
        this.b9_progress_bar = b9_progress_bar;
        this.database = database;
        this.document_id = document_id;
        this.user_id = user_id;
        this.context = context;
        this.b9_job_description = b9_job_description;
        this.auth = auth;
    }

    public void fetch_and_set_data() {
        String doc_id = document_id.toString().trim();
        database.collection("users").document(user_id).collection("jobPosted").document(doc_id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            String company = document.getString("company");
                            String role = document.getString("role");
                            String openings = document.getString("openings");
                            String jobType = document.getString("jobType");
                            String state = document.getString("state");
                            String city = document.getString("city");
                            String address = document.getString("address");
                            String email = document.getString("email");
                            String description = document.getString("description");
                            String info = document.getString("info");
                            b9_company_name.setText(company);
                            b9_job_role.setText(role);
                            b9_total_openings.setText(openings);
                            b9_job_type.setText(jobType);
                            b9_job_state.setText(state);
                            b9_job_city.setText(city);
                            b9_job_address.setText(address);
                            b9_job_email.setText(email);
                            b9_job_info.setText(info);
                            b9_job_description.setText(description);
                        }else{
                            Toast.makeText(context, "Document Empty", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                    }
                    // Hide the progress bar after data retrieval
                    b9_progress_bar.setVisibility(View.GONE);
                });
    }
    public void check_user_data() {
        String doc_id = document_id.toString().trim();
        if (user_id.equals(auth.getUid())) {
            Toast.makeText(context, "Cannot apply on your own job", Toast.LENGTH_LONG).show();
        } else {
            database.collection("users").document(Objects.requireNonNull(auth.getUid())).collection("jobApplied").document(doc_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            Toast.makeText(context, "Already applied for this job", Toast.LENGTH_LONG).show();
                        } else {
                            database.collection("users").document(Objects.requireNonNull(auth.getUid())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot userSnapshot = task.getResult();
                                        if (userSnapshot != null) {
                                            String name = userSnapshot.getString("name");
                                            String city = userSnapshot.getString("city");
                                            String state = userSnapshot.getString("state");

                                            if (name != null && city != null && state != null) {
                                                if (name.isEmpty() && city.isEmpty() && state.isEmpty()) {
                                                    Toast.makeText(context, "Complete your profile", Toast.LENGTH_LONG).show();
                                                    context.startActivity(new Intent(context, Backend7_Settings.class));
                                                } else {
                                                    Get_Date get_date = new Get_Date();
                                                    String date = get_date.getCurrentDate();
                                                    HashMap<String, Object> data = new HashMap<>();
                                                    data.put("user_id", auth.getUid());
                                                    data.put("date", date);
                                                    data.put("chat", "close");
                                                    data.put("status", "NA");
                                                    database.collection("users").document(user_id).
                                                            collection("jobPosted").document(doc_id).
                                                            collection("Applied_Job").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentReference documentReference = task.getResult();
                                                                String applicant_id = documentReference.getId();
                                                                HashMap<String, Object> notify = new HashMap<>();
                                                                notify.put("message", "You have applied for a new job");
                                                                notify.put("job_id", doc_id);
                                                                notify.put("date", date);

                                                                HashMap<String, Object> job_id = new HashMap<>();
                                                                job_id.put("job_id", doc_id);
                                                                job_id.put("hire_id", user_id);
                                                                job_id.put("applicant_id",applicant_id);

                                                                HashMap<String, Object> notify_1 = new HashMap<>();
                                                                notify_1.put("message", "Someone has applied for the job");
                                                                notify_1.put("job_id", doc_id);
                                                                notify_1.put("date", date);

                                                                database.collection("users").document(user_id).set(notify_1);
                                                                database.collection("users").document(auth.getUid()).collection("jobApplied").document(doc_id).set(job_id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            database.collection("users").document(auth.getUid()).collection("notification").add(notify).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        Toast.makeText(context, "Notification sent", Toast.LENGTH_SHORT).show();
                                                                                    } else {
                                                                                        Toast.makeText(context, "Failed to send notification", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                            Toast.makeText(context, "You have successfully applied for the job", Toast.LENGTH_LONG).show();
                                                                            context.startActivity(new Intent(context, Backend5_Profile.class));
                                                                        } else {
                                                                            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                                });
                                                            } else {
                                                                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            } else {
                                                Toast.makeText(context, "Incomplete profile data", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(context, "User document does not exist", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(context, "Error retrieving user data", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(context, "Error retrieving job application data", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


}
