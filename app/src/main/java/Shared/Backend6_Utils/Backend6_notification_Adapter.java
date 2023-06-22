package Shared.Backend6_Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tca.R;

import java.util.List;

public class Backend6_notification_Adapter extends RecyclerView.Adapter<Backend6_notification_Adapter.CardViewHolder> {
    List<Backend6_notification_GetterSetter> notify;
    Context context;

    public Backend6_notification_Adapter(List<Backend6_notification_GetterSetter> notify, Context context) {
        this.notify = notify;
        this.context = context;
    }

    @NonNull
    @Override
    public Backend6_notification_Adapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.card2_notification, parent, false);
        return new CardViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Backend6_notification_Adapter.CardViewHolder holder, int position) {
        Backend6_notification_GetterSetter get_set = notify.get(position);
        holder.b6_message.setText(get_set.getMessage());
        holder.b6_date.setText("Received on "+get_set.getDate());
    }

    @Override
    public int getItemCount() {
        return notify.size();
    }
    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView b6_name, b6_message, b6_date;
        ImageView b6_next;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            b6_name = itemView.findViewById(R.id.textView21);
            b6_message = itemView.findViewById(R.id.textView22);
            b6_date = itemView.findViewById(R.id.notify_time);
            b6_next = itemView.findViewById(R.id.notify_view);
        }
    }
}
