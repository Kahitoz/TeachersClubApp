package Shared;

import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Backend5_document_handler {
    FirebaseAuth auth;
    FirebaseFirestore database;
    RecyclerView b5_recyclerView;
    ProgressBar b5_progress_bar;
}
