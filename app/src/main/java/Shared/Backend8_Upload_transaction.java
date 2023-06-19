package Shared;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Backend8_Upload_transaction {
    Context context;
    EditText b8_Document_info;
    TextView b8_file_name;
    ProgressBar b8_progress_bar;
    FirebaseFirestore database;
    FirebaseAuth auth;

    public Backend8_Upload_transaction(Context context, EditText b8_Document_info, TextView b8_file_name, ProgressBar b8_progress_bar, FirebaseFirestore database, FirebaseAuth auth) {
        this.context = context;
        this.b8_Document_info = b8_Document_info;
        this.b8_file_name = b8_file_name;
        this.b8_progress_bar = b8_progress_bar;
        this.database = database;
        this.auth = auth;
    }
    public void upload_document(Uri fileUri, String selectedFileName) {
        b8_progress_bar.setVisibility(View.VISIBLE);

        // Get a reference to the Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String doc_id = UUID.randomUUID().toString();

        // Upload the document to Firebase Storage with the selected file name
        StorageReference documentRef = storageRef.child("documents/"+doc_id+"/"+selectedFileName);
        UploadTask uploadTask = documentRef.putFile(fileUri);

        // Monitor the upload progress
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get the download URL of the uploaded document
                documentRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // Store the download URL in Firestore database
                        storeDownloadUrl(downloadUri.toString(), selectedFileName);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors that occurred during getting the download URL
                        b8_progress_bar.setVisibility(View.GONE);
                        Toast.makeText(context,"Failed to get download URL.",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors that occurred during the upload
                b8_progress_bar.setVisibility(View.GONE);
                Toast.makeText(context,"Failed to upload document.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeDownloadUrl(String downloadUrl, String selectedFileName) {
        // Get the current user's UID
        String uid = auth.getUid();

        // Get a reference to the "users" collection
        CollectionReference usersRef = database.collection("users");

        // Get a reference to the current user's "Documents" collection
        assert uid != null;
        CollectionReference documentsRef = usersRef.document(uid).collection("Documents");

        // Create a new document to store the download URL
        DocumentReference newDocumentRef = documentsRef.document();
        Get_Date get_date = new Get_Date();
        String date = get_date.getCurrentDate();

        // Create a map to store the document data
        Map<String, Object> documentData = new HashMap<>();
        documentData.put("info", b8_Document_info.getText().toString());
        documentData.put("file_name", selectedFileName);
        documentData.put("download_url", downloadUrl);
        documentData.put("date", date);

        // Set the data of the new document
        newDocumentRef.set(documentData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document data was successfully stored
                        b8_progress_bar.setVisibility(View.GONE);
                        Toast.makeText(context,"Document uploaded successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors that occurred during storing the document data
                        b8_progress_bar.setVisibility(View.GONE);
                        Toast.makeText(context,"Failed to store document data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
