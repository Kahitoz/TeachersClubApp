package Shared.Backend5_utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tca.R;

public class Backend5_jobAdded_Adapter extends RecyclerView.Adapter<Backend5_jobAdded_Adapter.CardViewHolder> {

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card5_job_posted,parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class CardViewHolder extends RecyclerView.ViewHolder{

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
