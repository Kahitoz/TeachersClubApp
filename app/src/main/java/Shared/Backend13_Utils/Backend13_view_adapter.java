package Shared.Backend13_Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tca.R;

import java.util.List;

import Shared.Backend5_utils.Backend5_documents.Backend5_document_Adapter;
import Shared.Backend5_utils.Backend5_documents.Backend5_get_set_document;

public class Backend13_view_adapter extends RecyclerView.Adapter<Backend13_view_adapter.CardViewHolder> {
    private final List<Backend5_get_set_document> documentList; // Your dataset
    private final Context context;

    public Backend13_view_adapter(List<Backend5_get_set_document> documentList, Context context) {
        this.documentList = documentList;
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card3_document, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Backend5_get_set_document document = documentList.get(position);

        // Bind data to views
        holder.b5_file_name.setText(document.getFile_name());
        holder.b5_file_info.setText(document.getInfo());
        holder.b5_date.setText(document.getDate());

        final String downloadLink = document.getDownload_url();
        holder.b5_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for downloading the file using the downloadLink
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadLink));
                context.startActivity(browserIntent);
            }
        });

        holder.b5_delete.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView b5_file_name, b5_file_info, b5_date;
        ImageView b5_download, b5_delete;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            b5_file_name = itemView.findViewById(R.id.card3_document);
            b5_file_info = itemView.findViewById(R.id.card_doc_title);
            b5_date = itemView.findViewById(R.id.card_doc_date);
            b5_download = itemView.findViewById(R.id.card_doc_download);
            b5_delete = itemView.findViewById(R.id.card_doc_delete);
        }
    }
}