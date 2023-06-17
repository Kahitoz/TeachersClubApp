package Shared;

import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Backend3_Form_addJob {
    private final Spinner stateSpinner;
    private final Spinner jobTypeSpinner;
    private final EditText companyEditText;
    private final EditText roleEditText;
    private final EditText openingsEditText;
    private final EditText cityEditText;
    private final EditText addressEditText;
    private final EditText emailEditText;
    private final EditText descriptionEditText;
    private final EditText infoEditText;

    private final FirebaseAuth auth;
    private final FirebaseFirestore database;
    private final Context context;

    public Backend3_Form_addJob(Spinner stateSpinner, Spinner jobTypeSpinner, EditText companyEditText, EditText roleEditText,
                                EditText openingsEditText, EditText cityEditText, EditText addressEditText, EditText emailEditText,
                                EditText descriptionEditText, EditText infoEditText, Context context) {
        this.stateSpinner = stateSpinner;
        this.jobTypeSpinner = jobTypeSpinner;
        this.companyEditText = companyEditText;
        this.roleEditText = roleEditText;
        this.openingsEditText = openingsEditText;
        this.cityEditText = cityEditText;
        this.addressEditText = addressEditText;
        this.emailEditText = emailEditText;
        this.descriptionEditText = descriptionEditText;
        this.infoEditText = infoEditText;
        this.context = context;

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
    }

    public boolean validate_form() {
        boolean isValid = true;

        if (companyEditText.getText().toString().isEmpty()) {
            companyEditText.setError("Please enter company name");
            isValid = false;
        }

        if (roleEditText.getText().toString().isEmpty()) {
            roleEditText.setError("Please enter job role");
            isValid = false;
        }

        if (openingsEditText.getText().toString().isEmpty()) {
            openingsEditText.setError("Please enter number of openings");
            isValid = false;
        }

        if (cityEditText.getText().toString().isEmpty()) {
            cityEditText.setError("Please enter city");
            isValid = false;
        }

        if (addressEditText.getText().toString().isEmpty()) {
            addressEditText.setError("Please enter address");
            isValid = false;
        }

        if (emailEditText.getText().toString().isEmpty()) {
            emailEditText.setError("Please enter email");
            isValid = false;
        }

        if (descriptionEditText.getText().toString().isEmpty()) {
            descriptionEditText.setError("Please enter job description");
            isValid = false;
        }

        if (infoEditText.getText().toString().isEmpty()) {
            infoEditText.setError("Please enter additional info");
            isValid = false;
        }

        return isValid;
    }

    public void upload_job_data() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            String state = stateSpinner.getSelectedItem().toString();
            String jobType = jobTypeSpinner.getSelectedItem().toString();
            String company = companyEditText.getText().toString();
            String role = roleEditText.getText().toString();
            String openings = openingsEditText.getText().toString();
            String city = cityEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String info = infoEditText.getText().toString();
            String date = getCurrentDate();

            // Create a new job map
            Map<String, Object> jobMap = new HashMap<>();
            jobMap.put("state", state);
            jobMap.put("jobType", jobType);
            jobMap.put("company", company);
            jobMap.put("role", role);
            jobMap.put("openings", openings);
            jobMap.put("city", city);
            jobMap.put("address", address);
            jobMap.put("email", email);
            jobMap.put("description", description);
            jobMap.put("info", info);
            jobMap.put("status", "open");
            jobMap.put("date", date);

            CollectionReference jobPostedRef = database.collection("users").document(userId).collection("jobPosted");
            jobPostedRef.add(jobMap)
                    .addOnSuccessListener(documentReference -> {
                        String document_id = documentReference.getId();
                        jobMap.put("doc_id",document_id);
                        Toast.makeText(context, "Job created successfully", Toast.LENGTH_SHORT).show();
                        clearForm();
                    })
                    .addOnFailureListener(e -> Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void clearForm() {
        stateSpinner.setSelection(0);
        jobTypeSpinner.setSelection(0);
        companyEditText.setText("");
        roleEditText.setText("");
        openingsEditText.setText("");
        cityEditText.setText("");
        addressEditText.setText("");
        emailEditText.setText("");
        descriptionEditText.setText("");
        infoEditText.setText("");
    }

    public String getCurrentDate() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("d' 'MMMM', 'EEEE' 'yyyy", Locale.getDefault());

        return dateFormat.format(currentDate);
    }
}
