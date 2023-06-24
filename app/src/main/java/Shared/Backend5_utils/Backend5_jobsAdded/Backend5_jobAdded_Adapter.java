package Shared.Backend5_utils.Backend5_jobsAdded;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tca.Backend10_JobApplicants;
import com.example.tca.Backend5_Profile;
import com.example.tca.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

public class Backend5_jobAdded_Adapter extends RecyclerView.Adapter<Backend5_jobAdded_Adapter.CardViewHolder> {
    static List<Backend5_get_set_jobAdded> jobAdded;
    static Context context;

    public Backend5_jobAdded_Adapter(List<Backend5_get_set_jobAdded> jobAdded, Context context) {
        Backend5_jobAdded_Adapter.jobAdded = jobAdded;
        Backend5_jobAdded_Adapter.context = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card5_job_posted, parent, false);
        return new CardViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        int openColor = ContextCompat.getColor(context, R.color.green);
        Backend5_get_set_jobAdded jobAdded1 = jobAdded.get(position);
        holder.b5_job_name.setText(jobAdded1.getCompany());
        holder.b5_job_title.setText(jobAdded1.getRole());
        holder.b5_job_date.setText("posted on: " + jobAdded1.getDate());
        holder.b5_status.setText("Current Status: " + jobAdded1.getStatus());

        // Update the status handler based on the current status
        if (jobAdded1.getStatus().equals("close")) {
            holder.b5_status_handler.setText("open");
            holder.b5_status_handler.setTextColor(openColor);
        } else {
            holder.b5_status_handler.setText("close");
        }
    }

    @Override
    public int getItemCount() {
        return jobAdded.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView b5_job_name, b5_job_title, b5_job_date, b5_status, b5_status_handler;
        ImageView b5_view;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            b5_job_name = itemView.findViewById(R.id.card5_name);
            b5_job_title = itemView.findViewById(R.id.textView22);
            b5_job_date = itemView.findViewById(R.id.notify_time);
            b5_status = itemView.findViewById(R.id.card5_status);
            b5_status_handler = itemView.findViewById(R.id.card5_close);
            b5_view = itemView.findViewById(R.id.card5_view);
            b5_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Backend5_get_set_jobAdded jobAdded2 = jobAdded.get(position);
                    String job_id = jobAdded2.getDoc_id();
                    Intent intent = new Intent(context, Backend10_JobApplicants.class);
                    intent.putExtra("job_id", job_id);
                    context.startActivity(intent);
                }
            });

            b5_status_handler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Backend5_get_set_jobAdded jobAdded2 = jobAdded.get(position);
                    String job_id = jobAdded2.getDoc_id();

                    if (job_id != null) {
                        job_id = job_id.trim();


                        // Check the current status
                        String currentStatus = jobAdded2.getStatus();
                        String newStatus = currentStatus.equals("close") ? "open" : "close";

                        // Update the status in the database
                        updateJobStatus(job_id, newStatus);
                    }
                    else{
                        Toast.makeText(context, "JobId  null", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private static void updateJobStatus(String job_id, String newStatus) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        database.collection("users")
                .document(Objects.requireNonNull(auth.getUid())).collection("jobPosted").document(job_id.trim())
                .update("status", newStatus)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Status updated successfully", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, Backend5_Profile.class));
                            ((Activity) context).finish();
                        } else {
                            Toast.makeText(context, "Failed to update status", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
