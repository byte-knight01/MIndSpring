package com.sumit.mindspring.teacher;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.sumit.mindspring.R;
import com.sumit.mindspring.student.studentdashboard;

public class teacherlogin extends AppCompatActivity {
    private TextInputLayout registrationIdLayout, passwordLayout;
    private TextInputEditText registrationIdInput, passwordInput;
    private MaterialButton signInButton;
    private ImageButton backButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        registrationIdLayout = findViewById(R.id.registrationIdLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        registrationIdInput = findViewById(R.id.registrationIdInput);
        passwordInput = findViewById(R.id.passwordInput);
        signInButton = findViewById(R.id.signInButton);
        backButton = findViewById(R.id.backButton);

        // Check if user is already signed in
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(teacherlogin.this, teacherdashboard.class));
            finish();
        }

        // Set click listeners
        signInButton.setOnClickListener(v -> loginUser());
        backButton.setOnClickListener(v -> onBackPressed());

        // Forgot password listener
        findViewById(R.id.forgotPasswordText).setOnClickListener(v -> handleForgotPassword());
    }

    private void loginUser() {
        String registrationId = registrationIdInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Reset errors
        registrationIdLayout.setError(null);
        passwordLayout.setError(null);

        // Validation
        if (registrationId.isEmpty()) {
            registrationIdLayout.setError("Registration ID is required");
            registrationIdInput.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordLayout.setError("Password is required");
            passwordInput.requestFocus();
            return;
        }

        // Show loading state
        signInButton.setEnabled(false);
        signInButton.setText("Signing in...");

        // Convert registration ID to email format if needed
        String email = convertToEmail(registrationId);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    signInButton.setEnabled(true);
                    signInButton.setText("Sign in");

                    if (task.isSuccessful()) {
                        startActivity(new Intent(teacherlogin.this, teacherdashboard.class));
                        finish();
                    } else {
                        String errorMessage = task.getException() != null ?
                                task.getException().getMessage() :
                                "Authentication failed";
                        Toast.makeText(teacherlogin.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleForgotPassword() {
        String registrationId = registrationIdInput.getText().toString().trim();
        if (registrationId.isEmpty()) {
            registrationIdLayout.setError("Enter registration ID to reset password");
            registrationIdInput.requestFocus();
            return;
        }

        String email = convertToEmail(registrationId);
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(teacherlogin.this,
                                "Password reset email sent",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(teacherlogin.this,
                                "Failed to send reset email",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Helper method to convert registration ID to email if needed
    private String convertToEmail(String registrationId) {
        // Modify this method based on your email format
        // For example, if registration IDs are converted to emails by adding a domain:
        return registrationId + "@yourdomain.com";
        // Or return as is if registration ID is already an email:
        // return registrationId;
    }
}