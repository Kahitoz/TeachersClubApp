package Shared;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    public void add_adapter(){
        String[] indianStates = {"Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
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
                        String status = documentSnapshot.getString("phone");

                        b7_name.setText(name);
                    }
                }
            }
        });
    }

    public void update_operation(){

    }
}
