package Shared;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tca.Backend9_ViewJob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Backend9_Data_Retrieval {
    private TextView b9_company_name, b9_job_role, b9_total_openings, b9_job_type, b9_job_state, b9_job_city, b9_job_address, b9_job_email, b9_job_info, b9_job_description;
    private ProgressBar b9_progress_bar;
    private FirebaseFirestore database;

    private String document_id, user_id;
    private Context context;

    public Backend9_Data_Retrieval(Context context,TextView b9_company_name, TextView b9_job_role, TextView b9_total_openings, TextView b9_job_type, TextView b9_job_state, TextView b9_job_city, TextView b9_job_address, TextView b9_job_email, TextView b9_job_info, ProgressBar b9_progress_bar, FirebaseFirestore database, String document_id, String user_id, TextView b9_job_description) {
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
}
