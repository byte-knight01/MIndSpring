package com.sumit.mindspring.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.sumit.mindspring.select;

import java.util.HashMap;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_REGISTRATION_ID = "registration_id";
    private static final String KEY_ROLE = "role";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String registrationId, String role, String email, String name) {
        editor.putString(KEY_REGISTRATION_ID, registrationId);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_REGISTRATION_ID, pref.getString(KEY_REGISTRATION_ID, null));
        user.put(KEY_ROLE, pref.getString(KEY_ROLE, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        return user;
    }

    public boolean isLoggedIn() {
        return pref.getString(KEY_REGISTRATION_ID, null) != null;
    }

    public String getUserRole() {
        return pref.getString(KEY_ROLE, null);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        FirebaseAuth.getInstance().signOut();

        Intent i = new Intent(context, select.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }

    public void checkLogin() {
        if (!isLoggedIn()) {
            Intent i = new Intent(context, select.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);
        }
    }
}
