package com.sumit.mindspring;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sumit.mindspring.student.studentdashboard;

public class Splashscreen extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        // Start progress bar animation
        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 5; // Increment progress
                handler.post(() -> progressBar.setProgress(progressStatus));
                try {
                    Thread.sleep(100); // Delay to simulate progress
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Check authentication status after progress is complete
            handler.post(this::checkAuthentication);
        }).start();
    }

    private void checkAuthentication() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, navigate to Dashboard
            startActivity(new Intent(Splashscreen.this, studentdashboard.class));
        } else {
            // User is not signed in, navigate to Get Started screen
            startActivity(new Intent(Splashscreen.this, get_started.class));
        }
        finish(); // Finish Splashscreen Activity
    }
}
