package com.sumit.mindspring;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sumit.mindspring.admin.admindashboard;
import com.sumit.mindspring.student.studentdashboard;
import com.sumit.mindspring.teacher.teacherdashboard;
import com.sumit.mindspring.utils.SessionManager;

//public class Splashscreen extends AppCompatActivity {
//
//    private ProgressBar progressBar;
//    private int progressStatus = 0;
//    private Handler handler = new Handler();
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splashscreen);
//
//        progressBar = findViewById(R.id.progressBar);
//        mAuth = FirebaseAuth.getInstance();
//
//        // Start progress bar animation
//        new Thread(() -> {
//            while (progressStatus < 100) {
//                progressStatus += 5; // Increment progress
//                handler.post(() -> progressBar.setProgress(progressStatus));
//                try {
//                    Thread.sleep(100); // Delay to simulate progress
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            // Check authentication status after progress is complete
//            handler.post(this::checkAuthentication);
//        }).start();
//    }
//
//    private void checkAuthentication() {
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            // User is signed in, navigate to Dashboard
//            startActivity(new Intent(Splashscreen.this, studentdashboard.class));
//        } else {
//            // User is not signed in, navigate to Get Started screen
//            startActivity(new Intent(Splashscreen.this, get_started.class));
//        }
//        finish(); // Finish Splashscreen Activity
//    }
//}









//WORKING CODE

//public class Splashscreen extends AppCompatActivity {
//    private SessionManager sessionManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splashscreen);
//
//        sessionManager = new SessionManager(this);
//
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            if (sessionManager.isLoggedIn()) {
//                redirectBasedOnRole(sessionManager.getUserRole());
//            } else {
//                startActivity(new Intent(Splashscreen.this, get_started.class)); // Redirect to get started
//            }
//            finish();
//        }, 2000); // 2 seconds delay
//    }
//
//    private void redirectBasedOnRole(String role) {
//        Class<?> targetActivity;
//        switch (role) {
//            case "admin":
//                targetActivity = admindashboard.class;
//                break;
//            case "teacher":
//                targetActivity = teacherdashboard.class;
//                break;
//            case "student":
//                targetActivity = studentdashboard.class;
//                break;
//            default:
//                targetActivity = get_started.class;
//                break;
//        }
//        startActivity(new Intent(this, targetActivity));
//    }
//}


public class Splashscreen extends AppCompatActivity {
    private SessionManager sessionManager;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        sessionManager = new SessionManager(this);
        progressBar = findViewById(R.id.progressBar);

        // Initialize progress bar
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);

        // Start progress in a separate thread
        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 4; // Increment by 4 to complete in ~25 steps
                handler.post(() -> progressBar.setProgress(progressStatus));
                try {
                    Thread.sleep(80); // Total time will be ~2 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Once progress is complete, check auth and redirect
            handler.post(() -> {
                if (sessionManager.isLoggedIn()) {
                    redirectBasedOnRole(sessionManager.getUserRole());
                } else {
                    startActivity(new Intent(Splashscreen.this, get_started.class));
                }
                finish();
            });
        }).start();
    }

    private void redirectBasedOnRole(String role) {
        Class<?> targetActivity;
        switch (role) {
            case "admin":
                targetActivity = admindashboard.class;
                break;
            case "teacher":
                targetActivity = teacherdashboard.class;
                break;
            case "student":
                targetActivity = studentdashboard.class;
                break;
            default:
                targetActivity = get_started.class;
                break;
        }
        startActivity(new Intent(this, targetActivity));
    }
}
