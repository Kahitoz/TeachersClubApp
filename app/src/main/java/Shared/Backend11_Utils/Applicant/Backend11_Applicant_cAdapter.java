package Shared.Backend11_Utils.Applicant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tca.R;

import java.util.List;


public class Backend11_Applicant_cAdapter extends RecyclerView.Adapter<Backend11_Applicant_cAdapter.CardViewHolder> {
    List<Backend11_Applicant_getSet> getMessage;

    public Backend11_Applicant_cAdapter(List<Backend11_Applicant_getSet> getMessage) {
        this.getMessage = getMessage;
    }

    @NonNull
    @Override
    public Backend11_Applicant_cAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card7_chat_message, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Backend11_Applicant_cAdapter.CardViewHolder holder, int position) {
        Backend11_Applicant_getSet s_data = getMessage.get(position);
        holder.b11_name.setText(s_data.getName());
        holder.b11_message.setText(s_data.getMessage());
        holder.b11_date.setText(s_data.getDate());
    }

    @Override
    public int getItemCount() {
        return getMessage.size();
    }
    static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView b11_message, b11_date, b11_name;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            b11_message = itemView.findViewById(R.id.card7_message);
            b11_date = itemView.findViewById(R.id.card7_status);
            b11_name = itemView.findViewById(R.id.card7_name);
        }
    }
}
