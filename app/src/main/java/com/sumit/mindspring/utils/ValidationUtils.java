package com.sumit.mindspring.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationUtils {
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && phone.matches("^[0-9]{10}$");
    }

    public static boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }

    public static boolean isValidName(String name) {
        return !TextUtils.isEmpty(name) && name.length() >= 2;
    }
}
