package com.sumit.mindspring;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
//
//import com.sumit.accelify.admin.AdminDashboard;
//import com.sumit.accelify.student.StudentDashboard;
//import com.sumit.accelify.teacher.TeacherDashboard;

public class Splashscreen extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        progressBar = findViewById(R.id.progressBar);

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

            // After progress is complete, navigate to the next screen
            handler.post(() -> {
                Intent intent = new Intent(Splashscreen.this, get_started.class);
                startActivity(intent);
                finish(); // Finish Splashscreen Activity
            });
        }).start();
    }
}




//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//// Firebase imports
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//// Google Task/Listener imports
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.sumit.accelify_ed.admin.AdminDashboard;
//import com.sumit.accelify_ed.student.StudentDashboard;
//import com.sumit.accelify_ed.teacher.TeacherDashboard;
//
//
//public class Splashscreen extends AppCompatActivity {
//    private FirebaseAuth mAuth;
//    private FirebaseFirestore db;
//    private static final String TAG = "Splashscreen";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splashscreen);
//
//        // Initialize Firebase instances
//        mAuth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();
//
//        // Delay for 2 seconds to show splash screen
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                checkUserStatus();
//            }
//        }, 2000);
//    }
//
//    private void checkUserStatus() {
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser != null) {
//            // User is logged in, check their role
//            String uid = currentUser.getUid();
//
//            db.collection("users").document(uid)
//                    .get()
//                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            if (documentSnapshot.exists()) {
//                                String role = documentSnapshot.getString("role");
//                                redirectBasedOnRole(role);
//                            } else {
//                                // User document doesn't exist, logout and go to login
//                                mAuth.signOut();
//                                goToLogin();
//                            }
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(TAG, "Error getting user role", e);
//                            goToLogin();
//                        }
//                    });
//        } else {
//            // No user is logged in
//            goToLogin();
//        }
//    }
//
//    private void redirectBasedOnRole(String role) {
//        Intent intent;
//
//        switch (role.toLowerCase()) {
//            case "student":
//                intent = new Intent(Splashscreen.this, StudentDashboard.class);
//                break;
//            case "teacher":
//                intent = new Intent(Splashscreen.this, TeacherDashboard.class);
//                break;
//            case "admin":
//                intent = new Intent(Splashscreen.this, AdminDashboard.class);
//                break;
//            default:
//                // Invalid role, logout and go to login
//                mAuth.signOut();
//                goToLogin();
//                return;
//        }
//
//        // Start the appropriate dashboard activity
//        startActivity(intent);
//        finish(); // Close splash screen
//    }
//
//    private void goToLogin() {
//        Intent intent = new Intent(Splashscreen.this, get_started.class);
//        startActivity(intent);
//        finish(); // Close splash screen
//    }
//}