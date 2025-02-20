package com.sumit.mindspring.admin;

//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.ImageButton;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.FirebaseAuth;
//import com.sumit.mindspring.R;
//
//public class adminlogin extends AppCompatActivity {
//    private TextInputLayout registrationIdLayout, passwordLayout;
//    private TextInputEditText registrationIdInput, passwordInput;
//    private MaterialButton signInButton;
//    private ImageButton backButton;
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        // Initialize views
//        registrationIdLayout = findViewById(R.id.registrationIdLayout);
//        passwordLayout = findViewById(R.id.passwordLayout);
//        registrationIdInput = findViewById(R.id.registrationIdInput);
//        passwordInput = findViewById(R.id.passwordInput);
//        signInButton = findViewById(R.id.signInButton);
//        backButton = findViewById(R.id.backButton);
//
//        // Check if user is already signed in
//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(adminlogin.this, admindashboard.class));
//            finish();
//        }
//
//        // Set click listeners
//        signInButton.setOnClickListener(v -> loginUser());
//        backButton.setOnClickListener(v -> onBackPressed());
//
//        // Forgot password listener
//        findViewById(R.id.forgotPasswordText).setOnClickListener(v -> handleForgotPassword());
//    }
//
//    private void loginUser() {
//        String registrationId = registrationIdInput.getText().toString().trim();
//        String password = passwordInput.getText().toString().trim();
//
//        // Reset errors
//        registrationIdLayout.setError(null);
//        passwordLayout.setError(null);
//
//        // Validation
//        if (registrationId.isEmpty()) {
//            registrationIdLayout.setError("Registration ID is required");
//            registrationIdInput.requestFocus();
//            return;
//        }
//
//        if (password.isEmpty()) {
//            passwordLayout.setError("Password is required");
//            passwordInput.requestFocus();
//            return;
//        }
//
//        // Show loading state
//        signInButton.setEnabled(false);
//        signInButton.setText("Signing in...");
//
//        // Convert registration ID to email format if needed
//        String email = convertToEmail(registrationId);
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, task -> {
//                    signInButton.setEnabled(true);
//                    signInButton.setText("Sign in");
//
//                    if (task.isSuccessful()) {
//                        startActivity(new Intent(adminlogin.this, admindashboard.class));
//                        finish();
//                    } else {
//                        String errorMessage = task.getException() != null ?
//                                task.getException().getMessage() :
//                                "Authentication failed";
//                        Toast.makeText(adminlogin.this, errorMessage, Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void handleForgotPassword() {
//        String registrationId = registrationIdInput.getText().toString().trim();
//        if (registrationId.isEmpty()) {
//            registrationIdLayout.setError("Enter registration ID to reset password");
//            registrationIdInput.requestFocus();
//            return;
//        }
//
//        String email = convertToEmail(registrationId);
//        mAuth.sendPasswordResetEmail(email)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(adminlogin.this,
//                                "Password reset email sent",
//                                Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(adminlogin.this,
//                                "Failed to send reset email",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    // Helper method to convert registration ID to email if needed
//    private String convertToEmail(String registrationId) {
//        // Modify this method based on your email format
//        // For example, if registration IDs are converted to emails by adding a domain:
//        return registrationId + "@yourdomain.com";
//        // Or return as is if registration ID is already an email:
//        // return registrationId;
//    }
//}


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sumit.mindspring.R;
import com.sumit.mindspring.utils.SessionManager;

import java.util.Objects;

public class adminlogin extends AppCompatActivity {
    private TextInputEditText registrationIdInput, passwordInput;
    private MaterialButton loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        sessionManager = new SessionManager(this);

        // Initialize views
        registrationIdInput = findViewById(R.id.registrationIdInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.signInButton);
        progressBar = findViewById(R.id.progressBar); // Ensure this exists in your layout

        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String registrationId = Objects.requireNonNull(registrationIdInput.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordInput.getText()).toString().trim();

        if (!validateInputs(registrationId, password)) return;

        setLoading(true);

        // Query Firestore to get user with this registration ID
        db.collection("users")
                .whereEqualTo("registrationId", registrationId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        String email = document.getString("email");
                        String role = document.getString("role");

                        if (!"admin".equals(role)) {
                            setLoading(false);
                            showError("Access denied. Admin access only.");
                            return;
                        }

                        // Login with email and password
                        loginWithEmailPassword(email, password, document);
                    } else {
                        setLoading(false);
                        showError("Invalid registration ID");
                    }
                })
                .addOnFailureListener(e -> {
                    setLoading(false);
                    showError(e.getMessage());
                });
    }

    private void loginWithEmailPassword(String email, String password, DocumentSnapshot userDoc) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    setLoading(false);
                    if (task.isSuccessful()) {
                        // Create session
                        sessionManager.createLoginSession(
                                userDoc.getString("registrationId"),
                                userDoc.getString("role"),
                                email,
                                userDoc.getString("name")
                        );

                        // Redirect to dashboard
                        startActivity(new Intent(adminlogin.this, admindashboard.class));
                        finish();
                    } else {
                        showError("Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    private boolean validateInputs(String registrationId, String password) {
        if (registrationId.isEmpty()) {
            registrationIdInput.setError("Registration ID is required");
            return false;
        }

        if (password.isEmpty()) {
            passwordInput.setError("Password is required");
            return false;
        }

        return true;
    }

    private void setLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        loginButton.setEnabled(!isLoading);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
