package Shared.Backeng6_Utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tca.Backend6_Notification;

public class Backend6_notification_Adapter extends RecyclerView.Adapter<Backend6_notification_Adapter.CardViewHolder> {

    @NonNull
    @Override
    public Backend6_notification_Adapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Backend6_notification_Adapter.CardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    static class CardViewHolder extends RecyclerView.ViewHolder {
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
