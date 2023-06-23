package Shared.Backend10_Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tca.Backend10_JobApplicants;
import com.example.tca.Backend13_ViewProfile;
import com.example.tca.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Backend10_jobApplicants_adapter extends RecyclerView.Adapter<Backend10_jobApplicants_adapter.CardViewHolder> {
    static Context context;
    String job_id;
    static List<Backend10_jobApplicants_getterSetter> applicants_list;
    public Backend10_jobApplicants_adapter(Context context, List<Backend10_jobApplicants_getterSetter> applicants_list, String job_id) {
        this.context = context;
        Backend10_jobApplicants_adapter.applicants_list = applicants_list;
        this.job_id = job_id;
    }


    @NonNull
    @Override
    public Backend10_jobApplicants_adapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card6_job_applicants,parent, false);
        return new CardViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final Backend10_jobApplicants_adapter.CardViewHolder holder, final int position) {
        Backend10_jobApplicants_getterSetter get_data = applicants_list.get(position);
        final String get_uid = get_data.getDoc_id();
        String get_user_id = get_data.getUser_id();
        get_user_name(get_user_id, holder);
        String get_chat = get_data.getChat();
        if(get_chat.equals("open")){
            holder.b10_switch.setChecked(true);
        }else{
            holder.b10_switch.setChecked(false);
        }
        holder.b10_status.setText(get_data.getStatus());
        holder.b10_date.setText(get_data.getDate());

        String status = get_data.getStatus();
        switch (status) {
            case "accepted":
                holder.b10_accept.setVisibility(View.INVISIBLE);
                holder.b10_reject.setVisibility(View.VISIBLE);
                break;
            case "rejected":
                holder.b10_accept.setVisibility(View.VISIBLE);
                holder.b10_reject.setVisibility(View.INVISIBLE);
                break;
            case "NA":
                holder.b10_accept.setVisibility(View.VISIBLE);
                holder.b10_reject.setVisibility(View.VISIBLE);
                break;
        }

        holder.b10_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_accepted(get_uid);
                holder.b10_accept.setVisibility(View.INVISIBLE);
                holder.b10_reject.setVisibility(View.VISIBLE);
            }
        });

        holder.b10_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_rejected(get_uid);
                holder.b10_accept.setVisibility(View.VISIBLE);
                holder.b10_reject.setVisibility(View.INVISIBLE);
            }
        });

        holder.b10_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.b10_status.setText("NA");
                holder.b10_switch.setChecked(false);
                reset(get_uid);
            }
        });

        holder.b10_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String uid = get_data.getDoc_id();
                if (isChecked) {
                    chat_open(uid);
                } else {
                    chat_close(uid);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return applicants_list.size();
    }
    static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView b10_name, b10_status, b10_accept, b10_reject, b10_date, b10_reset, b10_view_profile;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch b10_switch;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            b10_name = itemView.findViewById(R.id.card6_name);
            b10_status = itemView.findViewById(R.id.card6_status);
            b10_accept = itemView.findViewById(R.id.card6_accept);
            b10_reject = itemView.findViewById(R.id.card6_reject);
            b10_date = itemView.findViewById(R.id.card6_date);
            b10_reset = itemView.findViewById(R.id.card6_reset);
            b10_switch = itemView.findViewById(R.id.card6_Switch);
            b10_view_profile = itemView.findViewById(R.id.card6_view_profile);

            b10_view_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Backend10_jobApplicants_getterSetter data = applicants_list.get(position);
                    Intent intent = new Intent(context, Backend13_ViewProfile.class);
                    intent.putExtra("uid", data.getUser_id().trim());
                    context.startActivity(intent);
                }
            });

        }
    }
    public void get_user_name(String uid, Backend10_jobApplicants_adapter.CardViewHolder holder){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();
                    if(snapshot!=null){
                        String name = snapshot.getString("name");
                        holder.b10_name.setText(name);
                    }
                }
            }
        });
    }
    public void update_accepted(String u_id){
        Toast.makeText(context, "User Accepted", Toast.LENGTH_SHORT).show();
        FirebaseFirestore database;
        FirebaseAuth auth;
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        database.collection("users").
                document(Objects.requireNonNull(auth.getUid())).
                collection("jobPosted").
                document(job_id.trim()).
                collection("Applied_Job").
                document(u_id).update("status","accepted");
    }

    public void update_rejected(String u_id){
        Toast.makeText(context,"User Rejected",Toast.LENGTH_SHORT).show();
        FirebaseFirestore database;
        FirebaseAuth auth;
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        database.collection("users").
                document(Objects.requireNonNull(auth.getUid())).
                collection("jobPosted").
                document(job_id.trim()).
                collection("Applied_Job").
                document(u_id).update("status","rejected");
    }

    public void chat_open(String uid){
        Toast.makeText(context,"Chat opened",Toast.LENGTH_SHORT).show();
        FirebaseFirestore database;
        FirebaseAuth auth;
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        database.collection("users").
                document(Objects.requireNonNull(auth.getUid())).
                collection("jobPosted").
                document(job_id.trim()).
                collection("Applied_Job").
                document(uid).update("chat","open").addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firebase Chat error", e.getMessage());
                    }
                });
    }
    public void chat_close(String uid){
        Toast.makeText(context,"Chat Closed",Toast.LENGTH_SHORT).show();
        FirebaseFirestore database;
        FirebaseAuth auth;
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        database.collection("users").
                document(Objects.requireNonNull(auth.getUid())).
                collection("jobPosted").
                document(job_id.trim()).
                collection("Applied_Job").
                document(uid).update("chat","close");
    }
    public void reset(String uid){
        Toast.makeText(context,"Reset to default settings",Toast.LENGTH_SHORT).show();
        FirebaseFirestore database;
        FirebaseAuth auth;
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        String status = "NA";
        String chat = "close";
        HashMap<String, Object> data = new HashMap<>();
        data.put("status", status);
        data.put("chat", chat);
        database.collection("users").
                document(Objects.requireNonNull(auth.getUid())).
                collection("jobPosted").
                document(job_id.trim()).
                collection("Applied_Job").
                document(uid).update(data).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseJobError", "Error updating document: " + e.getMessage());
                    }
                });
    }
}
