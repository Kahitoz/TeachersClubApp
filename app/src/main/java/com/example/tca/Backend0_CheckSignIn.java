package com.example.tca;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Backend0_CheckSignIn extends AppCompatActivity {
    private static final String TAG = "CheckSignIn";
    private static final String COLLECTION_GLOBAL_UPDATE = "GlobalUpdate";
    private static final String DOCUMENT_MESSAGE = "message";
    private static final String FIELD_VERSION = "version";
    private static final String FIELD_USER_INFO = "user_info";

    private final String appVersion = "1.0.0";
    private FirebaseAuth auth;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui0_check_sign_in);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        checkAppVersion();
    }

    private void checkAppVersion() {
        DocumentReference globalUpdateRef = database.collection(COLLECTION_GLOBAL_UPDATE).document(DOCUMENT_MESSAGE);
        globalUpdateRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String serverVersion = document.getString(FIELD_VERSION);
                        if (serverVersion != null && !serverVersion.equals(appVersion)) {
                            // Different app version, change activity to Backend12_UpdatePage.class
                            startActivity(new Intent(Backend0_CheckSignIn.this, Backend12_UpdatePage.class));
                            finish();
                        } else {
                            signInUser();
                        }
                    } else {
                        Log.d(TAG, "Document does not exist");
                    }
                } else {
                    Log.d(TAG, "Failed to get app version: ", task.getException());
                }
            }
        });
    }

    private void signInUser() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DocumentReference userDocRef = database.collection("users").document(uid);
            userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            // Document with the same UID exists, change activity to Backend2_Dashboard.class
                            startActivity(new Intent(Backend0_CheckSignIn.this, Backend2_Dashboard.class));
                            finish();
                        } else {
                            // Document with the UID does not exist, create it
                            createUserDocument(uid);
                        }
                    } else {
                        Log.d(TAG, "Failed to get user document: ", task.getException());
                    }
                }
            });
        } else {
            Log.d(TAG, "User not signed in");
            // Redirect to sign-in activity or handle sign-in process
        }
    }

    private void createUserDocument(String uid) {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String name = user.getDisplayName();
            String city = "City"; // Replace with the actual city value
            String state = "State"; // Replace with the actual state value
            int numDocumentsUploaded = 0; // Replace with the actual number of documents uploaded

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", email);
            userInfo.put("name", name);
            userInfo.put("city", city);
            userInfo.put("state", state);
            userInfo.put("num_documents_uploaded", numDocumentsUploaded);

            DocumentReference userDocRef = database.collection("users").document(uid);
            userDocRef.set(userInfo)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Document created successfully, change activity to Backend2_Dashboard.class
                                startActivity(new Intent(Backend0_CheckSignIn.this, Backend2_Dashboard.class));
                                finish();
                            } else {
                                Log.d(TAG, "Failed to create user document: ", task.getException());
                            }
                        }
                    });
        } else {
            Log.d(TAG, "User not signed in");
            // Redirect to sign-in activity or handle sign-in process
        }
    }
}
