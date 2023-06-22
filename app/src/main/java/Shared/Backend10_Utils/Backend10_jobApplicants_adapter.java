package Shared.Backend10_Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tca.R;

import java.util.List;

public class Backend10_jobApplicants_adapter extends RecyclerView.Adapter<Backend10_jobApplicants_adapter.CardViewHolder> {
    Context context;

    public Backend10_jobApplicants_adapter(Context context, List<Backend10_jobApplicants_getterSetter> applicants_list) {
        this.context = context;
        this.applicants_list = applicants_list;
    }

    List<Backend10_jobApplicants_getterSetter> applicants_list;
    @NonNull
    @Override
    public Backend10_jobApplicants_adapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card6_job_applicants,parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Backend10_jobApplicants_adapter.CardViewHolder holder, int position) {
        Backend10_jobApplicants_getterSetter get_data = applicants_list.get(position);
        holder.b10_status.setText(get_data.getStatus());
        holder.b10_date.setText(get_data.getDate());
    }

    @Override
    public int getItemCount() {
        return applicants_list.size();
    }
    static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView b10_name, b10_status, b10_accept, b10_reject, b10_date, b10_reset;
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

        }
    }
}
