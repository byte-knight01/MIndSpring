package com.sumit.mindspring.utils;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sumit.mindspring.models.Admin;
import com.sumit.mindspring.models.Student;
import com.sumit.mindspring.models.Teacher;
import com.sumit.mindspring.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FirebaseHelper {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Context context;

    public FirebaseHelper(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void createUser(User user, String password, OnCompleteListener listener) {
        // Generate registration ID if not present
        if (user.getRegistrationId() == null || user.getRegistrationId().isEmpty()) {
            user.setRegistrationId(generateRegistrationId());
        }

        // Create authentication account
        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = task.getResult().getUser().getUid();
                        storeUserData(uid, user, listener);
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }

    private void storeUserData(String uid, User user, OnCompleteListener listener) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("registrationId", user.getRegistrationId());
        userData.put("email", user.getEmail());
        userData.put("phoneNo", user.getPhoneNo());
        userData.put("name", user.getName());
        userData.put("role", user.getRole());

        // Add role-specific data
        if (user instanceof Student) {
            Student student = (Student) user;
            userData.put("className", student.getClassName());
            userData.put("parentName", student.getParentName());
            userData.put("parentPhone", student.getParentPhone());
        } else if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            userData.put("subject", teacher.getSubject());
        } else if (user instanceof Admin) {
            Admin admin = (Admin) user;
            userData.put("position", admin.getPosition());
        }

        db.collection("users").document(uid)
                .set(userData)
                .addOnSuccessListener(aVoid -> listener.onSuccess("User created successfully"))
                .addOnFailureListener(e -> {
                    // Delete the authentication account if Firestore storage fails
                    mAuth.getCurrentUser().delete();
                    listener.onFailure(e.getMessage());
                });
    }

    private String generateRegistrationId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public interface OnCompleteListener {
        void onSuccess(String message);
        void onFailure(String error);
    }
}
