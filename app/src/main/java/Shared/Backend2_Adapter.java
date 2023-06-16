package Shared;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tca.R;

import java.util.List;


public class Backend2_Adapter extends RecyclerView.Adapter<Backend2_Adapter.CardViewHolder> {
    private final List<Backend2_GetAndSet> dataList;
    public Backend2_Adapter(List<Backend2_GetAndSet> dataList) {
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public Backend2_Adapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card1_dashboard, parent, false);
        return new CardViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Backend2_Adapter.CardViewHolder holder, int position) {
        Backend2_GetAndSet item = dataList.get(position);
        holder.b2_company_name.setText(item.getCompany());
        holder.b2_date.setText(item.getDate());
        holder.b2_jobTitle.setText(item.getRole());
        holder.b2_total_openings.setText(item.getOpenings());
        holder.b2_location.setText(item.getCity() + ", " + item.getState());
        holder.b2_job_type.setText(item.getJobType());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView b2_company_name, b2_date, b2_jobTitle, b2_total_openings, b2_location, b2_job_type;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

                  b2_company_name = itemView.findViewById(R.id.view_name);
                  b2_date =itemView.findViewById(R.id.view_date);
                  b2_jobTitle =itemView.findViewById(R.id.view_title);
                  b2_total_openings =itemView.findViewById(R.id.view_openings);
                  b2_location  =itemView.findViewById(R.id.view_location);
                  b2_job_type =itemView.findViewById(R.id.view_type);

        }
    }
}
