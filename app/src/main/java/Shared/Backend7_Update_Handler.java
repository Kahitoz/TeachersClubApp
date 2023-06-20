package Shared;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tca.Backend1_Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Backend7_Update_Handler {
    EditText b7_name, b7_phone_number, b7_city, b7_status;
    Spinner b7_state;
    FirebaseAuth auth;
    FirebaseFirestore database;
    Context context;

    public Backend7_Update_Handler(Context context, EditText b7_name, EditText b7_phone_number, EditText b7_city, EditText b7_status, Spinner b7_state, FirebaseAuth auth, FirebaseFirestore database) {
        this.b7_name = b7_name;
        this.b7_phone_number = b7_phone_number;
        this.b7_city = b7_city;
        this.b7_status = b7_status;
        this.b7_state = b7_state;
        this.auth = auth;
        this.database = database;
        this.context = context;
    }
    public void logout() {
        auth.signOut();
        Intent intent = new Intent(context, Backend1_Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void add_adapter(){
        String[] indianStates = {"","Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, indianStates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        b7_state.setAdapter(adapter);
    }
    public void check_and_update(){
        database.collection("users").document(Objects.requireNonNull(auth.getUid())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot!=null){

                        String name = documentSnapshot.getString("name");
                        String state = documentSnapshot.getString("state");
                        String city = documentSnapshot.getString("city");
                        String phone = documentSnapshot.getString("phone");
                        String status = documentSnapshot.getString("status");

                        b7_name.setText(name);
                        b7_city.setText(city);
                        b7_phone_number.setText(phone);
                        b7_status.setText(status);
                        b7_state.setSelection(getSpinnerIndex(b7_state, state));
                    }
                }else{
                    Toast.makeText(context, "task Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int getSpinnerIndex(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (TextUtils.equals(adapter.getItem(i), value)) {
                    return i;
                }
            }
        }
        return 0; // Default index
    }


    public void update_operation() {
        final String newName = b7_name.getText().toString().trim();
        final String newCity = b7_city.getText().toString().trim();
        final String newPhone = b7_phone_number.getText().toString().trim();
        final String newState = b7_state.getSelectedItem().toString().trim();
        final String newStatus = b7_status.getText().toString().trim();

        // Fetch the existing document from Firestore
        DocumentReference userRef = database.collection("users").document(Objects.requireNonNull(auth.getUid()));
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null) {
                        // Get the existing values from the document
                        String existingName = documentSnapshot.getString("name");
                        String existingCity = documentSnapshot.getString("city");
                        String existingPhone = documentSnapshot.getString("phone");
                        String existingState = documentSnapshot.getString("state");
                        String existingStatus = documentSnapshot.getString("status");

                        // Check if any field values have changed
                        boolean isNameChanged = !newName.equals(existingName);
                        boolean isCityChanged = !newCity.equals(existingCity);
                        boolean isPhoneChanged = !newPhone.equals(existingPhone);
                        boolean isStateChanged = !newState.equals(existingState);
                        boolean isStatusChanged = !newStatus.equals(existingStatus);

                        // Create a map to hold the updated values
                        Map<String, Object> updates = new HashMap<>();

                        // Update the map only for the fields that have changed
                        if (isNameChanged) {
                            updates.put("name", newName);
                        }
                        if (isCityChanged) {
                            updates.put("city", newCity);
                        }
                        if (isPhoneChanged) {
                            updates.put("phone", newPhone);
                        }
                        if (isStateChanged) {
                            updates.put("state", newState);
                        }
                        if (isStatusChanged) {
                            updates.put("status", newStatus);
                        }

                        // Perform the update if there are any changes
                        if (!updates.isEmpty()) {
                            userRef.update(updates)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(context, "No changes to update", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(context, "Fetch failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
