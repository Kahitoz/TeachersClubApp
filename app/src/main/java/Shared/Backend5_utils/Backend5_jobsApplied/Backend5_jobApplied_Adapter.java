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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import Shared.Backend5_utils.Backend5_jobsAdded.Backend5_jobAdded_Adapter;

public class Backend5_jobApplied_Adapter extends RecyclerView.Adapter<Backend5_jobApplied_Adapter.CardViewHolder> {
static List<Backend5_jobApplied_GetterSetter> jobs_applied;
Context context;

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

    }

    @Override
    public int getItemCount() {
        return jobs_applied.size();
    }
    static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView b5_name, b5_title, b5_date, b5_status;
        ImageView b5_view;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            b5_name = itemView.findViewById(R.id.card5_name);
            b5_title = itemView.findViewById(R.id.textView22);
            b5_status = itemView.findViewById(R.id.card5_status);
            b5_date = itemView.findViewById(R.id.notify_time);
            b5_view = itemView.findViewById(R.id.card5_view);

            int position = getAdapterPosition();
            Backend5_jobApplied_GetterSetter backend5_jobApplied_getterSetter = jobs_applied.get(position);
            String hire_id = backend5_jobApplied_getterSetter.getHire_id();
            String job_id = backend5_jobApplied_getterSetter.getJob_id();
            String applicant_id = backend5_jobApplied_getterSetter.getApplicant_id();

        }
    }
    public String[] get_data(String hire_id, String job_id, String applicant_id){
        FirebaseFirestore database =FirebaseFirestore.getInstance();
        database.collection("users").document(hire_id).collection("jobsPosted").document(job_id).collection("applicants").document(applicant_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            }
        });
        return null;
    }
}
