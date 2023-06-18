package com.example.tca;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tca.Backend5_Profile;
import com.example.tca.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

import Shared.Backend8_Upload_transaction;

public class Backend8_AddDocument extends AppCompatActivity {
    ImageView b8_Back;
    EditText b8_Document_info;
    TextView b8_file_name;
    ProgressBar b8_progress_bar;
    FirebaseAuth auth;
    FirebaseFirestore database;
    Button b8_upload_button;

    private static final int PICK_FILE_REQUEST_CODE = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui8_add_document);
        b8_Back = findViewById(R.id.ui8_Back);
        b8_Document_info = findViewById(R.id.document_description);
        b8_file_name = findViewById(R.id.ui8_file_name);
        b8_progress_bar = findViewById(R.id.document_progress);
        b8_progress_bar.setVisibility(View.INVISIBLE);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        Backend8_Upload_transaction backend8_upload_transaction = new Backend8_Upload_transaction(this, b8_Document_info, b8_file_name, b8_progress_bar, database, auth);

        b8_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Backend5_Profile.class));
                finish();
            }
        });

        b8_upload_button = findViewById(R.id.document_upload);
        b8_upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open file picker to select a file
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            String selectedFileName = getSelectedFileName(fileUri);
            if (selectedFileName != null) {
                Backend8_Upload_transaction backend = new Backend8_Upload_transaction(this, b8_Document_info, b8_file_name, b8_progress_bar, database, auth);
                backend.upload_document(fileUri, selectedFileName);
            } else {
                showToast("Failed to retrieve the selected file name.");
            }
        }
    }

    private String getSelectedFileName(Uri fileUri) {
        // Retrieve the selected file name from the file Uri
        String fileName = null;
        if (fileUri.getScheme().equals("content")) {
            String[] filePathColumn = {MediaStore.MediaColumns.DISPLAY_NAME};
            try (Cursor cursor = getContentResolver().query(fileUri, filePathColumn, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    fileName = cursor.getString(columnIndex);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (fileUri.getScheme().equals("file")) {
            fileName = new File(fileUri.getPath()).getName();
        }
        return fileName;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
