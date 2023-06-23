package Shared.Backend5_utils.Backend5_jobsApplied;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tca.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import Shared.Backend5_utils.Backend5_jobsApplied.Backend5_jobApplied_GetterSetter;

public class Backend5_jobApplied_Adapter extends RecyclerView.Adapter<Backend5_jobApplied_Adapter.CardViewHolder> {
    private final List<Backend5_jobApplied_GetterSetter> jobs_applied;
    private Context context;

    public Backend5_jobApplied_Adapter(List<Backend5_jobApplied_GetterSetter> jobs_applied, Context context) {
        this.jobs_applied = jobs_applied;
        this.context = context;
    }

    @NonNull
    @Override
    public Backend5_jobApplied_Adapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card4_job_applied, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Backend5_jobApplied_Adapter.CardViewHolder holder, int position) {
        Backend5_jobApplied_GetterSetter backend5_jobApplied_getterSetter = jobs_applied.get(position);
        String hire_id = backend5_jobApplied_getterSetter.getHire_id();
        String job_id = backend5_jobApplied_getterSetter.getJob_id();
        String applicant_id = backend5_jobApplied_getterSetter.getApplicant_id();

        get_data(hire_id, job_id, applicant_id, holder);
    }

    @Override
    public int getItemCount() {
        return jobs_applied.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView b5_name, b5_title, b5_date, b5_status, b5_chat;
        ImageView b5_view;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            b5_name = itemView.findViewById(R.id.card5_name);
            b5_title = itemView.findViewById(R.id.textView22);
            b5_status = itemView.findViewById(R.id.card5_status);
            b5_date = itemView.findViewById(R.id.notify_time);
            b5_view = itemView.findViewById(R.id.card5_view);
            b5_chat = itemView.findViewById(R.id.card4_chat);
        }
    }

    private void get_data(String hire_id, String job_id, String applicant_id, Backend5_jobApplied_Adapter.CardViewHolder holder) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("users").document(hire_id.trim()).collection("jobPosted").document(job_id.trim()).collection("Applied_Job").document(applicant_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot != null) {
                    String date = documentSnapshot.getString("date");
                    String status = documentSnapshot.getString("status");
                    String chat = documentSnapshot.getString("chat");

                    holder.b5_date.setText(date);
                    holder.b5_status.setText(status);

                    if ("close".equals(chat)) {
                        holder.b5_chat.setVisibility(View.GONE);
                    } else if ("open".equals(chat)) {
                        holder.b5_chat.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        database.collection("users").document(hire_id).collection("jobPosted").document(job_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot != null) {
                    String title = documentSnapshot.getString("role");
                    String name = documentSnapshot.getString("company");
                    holder.b5_title.setText(title);
                    holder.b5_name.setText(name);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                holder.b5_name.setText(e.getMessage());
            }
        });
    }
}
