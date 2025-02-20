package com.sumit.mindspring.admin;

//public class CreateUserFragment extends Fragment {
//    private Spinner roleSpinner, classSpinner;
//    private EditText emailInput, nameInput, phoneInput, passwordInput;
//    private LinearLayout studentFields, teacherFields;
//    private EditText parentNameInput, parentPhoneInput, subjectInput;
//    private Button createButton;
//    private ProgressBar progressBar;
//    private FirebaseHelper firebaseHelper;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_create_user, container, false);
//
//        initializeViews(view);
//        setupSpinners();
//        setupListeners();
//
//        firebaseHelper = new FirebaseHelper(requireContext());
//
//        return view;
//    }
//
//    private void initializeViews(View view) {
//        roleSpinner = view.findViewById(R.id.roleSpinner);
//        classSpinner = view.findViewById(R.id.classSpinner);
//        emailInput = view.findViewById(R.id.emailInput);
//        nameInput = view.findViewById(R.id.nameInput);
//        phoneInput = view.findViewById(R.id.phoneInput);
//        passwordInput = view.findViewById(R.id.passwordInput);
//        studentFields = view.findViewById(R.id.studentFields);
//        teacherFields = view.findViewById(R.id.teacherFields);
//        parentNameInput = view.findViewById(R.id.parentNameInput);
//        parentPhoneInput = view.findViewById(R.id.parentPhoneInput);
//        subjectInput = view.findViewById(R.id.subjectInput);
//        createButton = view.findViewById(R.id.createButton);
//        progressBar = view.findViewById(R.id.progressBar);
//    }
//
//    private void setupSpinners() {
//        // Set up role spinner
//        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(
//                requireContext(),
//                R.array.user_roles,
//                android.R.layout.simple_spinner_item
//        );
//        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        roleSpinner.setAdapter(roleAdapter);
//
//        // Set up class spinner
//        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(
//                requireContext(),
//                R.array.class_list,
//                android.R.layout.simple_spinner_item
//        );
//        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        classSpinner.setAdapter(classAdapter);
//    }
//
//    private void setupListeners() {
//        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                updateFieldsVisibility(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
//
//        createButton.setOnClickListener(v -> createUser());
//    }
//
//    private void updateFieldsVisibility(int position) {
//        studentFields.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
//        teacherFields.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
//    }
//
//    private void createUser() {
//        if (!validateInputs()) return;
//
//        setLoading(true);
//
//        String email = emailInput.getText().toString().trim();
//        String name = nameInput.getText().toString().trim();
//        String phone = phoneInput.getText().toString().trim();
//        String password = passwordInput.getText().toString().trim();
//        String role = roleSpinner.getSelectedItem().toString().toLowerCase();
//
//        User user;
//        switch (role) {
//            case "student":
//                user = new Student(
//                        null, // registrationId will be generated
//                        email,
//                        phone,
//                        name,
//                        classSpinner.getSelectedItem().toString(),
//                        parentNameInput.getText().toString().trim(),
//                        parentPhoneInput.getText().toString().trim()
//                );
//                break;
//            case "teacher":
//                user = new Teacher(
//                        null,
//                        email,
//                        phone,
//                        name,
//                        subjectInput.getText().toString().trim()
//                );
//                break;
//            default:
//                user = new Admin(
//                        null,
//                        email,
//                        phone,
//                        name,
//                        "Staff" // Default position
//                );
//        }
//
//        firebaseHelper.createUser(user, password, new FirebaseHelper.OnCompleteListener() {
//            @Override
//            public void onSuccess(String message) {
//                setLoading(false);
//                showSuccessDialog(user.getRegistrationId());
//                clearInputs();
//            }
//
//            @Override
//            public void onFailure(String error) {
//                setLoading(false);
//                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private boolean validateInputs() {
//        // Add validation logic here
//        return true;
//    }
//
//    private void setLoading(boolean isLoading) {
//        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
//        createButton.setEnabled(!isLoading);
//    }
//
//    private void showSuccessDialog(String registrationId) {
//        new AlertDialog.Builder(requireContext())
//                .setTitle("User Created Successfully")
//                .setMessage("Registration ID: " + registrationId +
//                        "\n\nPlease save this ID for login purposes.")
//                .setPositiveButton("OK", null)
//                .show();
//    }
//
//    private void clearInputs() {
//        emailInput.setText("");
//        nameInput.setText("");
//        phoneInput.setText("");
//        passwordInput.setText("");
//        parentNameInput.setText("");
//        parentPhoneInput.setText("");
//        subjectInput.setText("");
//    }
//}


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.sumit.mindspring.R;
import com.sumit.mindspring.models.Admin;
import com.sumit.mindspring.models.Student;
import com.sumit.mindspring.models.Teacher;
import com.sumit.mindspring.models.User;
import com.sumit.mindspring.utils.FirebaseHelper;

//public class CreateUserFragment extends Fragment {
//    private Spinner roleSpinner, classSpinner;
//    private EditText emailInput, nameInput, phoneInput, passwordInput;
//    private LinearLayout studentFields, teacherFields;
//    private EditText parentNameInput, parentPhoneInput, subjectInput;
//    private Button createButton;
//    private ProgressBar progressBar;
//    private FirebaseHelper firebaseHelper;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_create_user, container, false);
//        initializeViews(view);
//        setupSpinners();
//        setupListeners();
//        firebaseHelper = new FirebaseHelper(requireContext());
//        return view;
//    }
//
//    private void initializeViews(View view) {
//        roleSpinner = view.findViewById(R.id.roleSpinner);
//        classSpinner = view.findViewById(R.id.classSpinner);
//        emailInput = view.findViewById(R.id.emailInput);
//        nameInput = view.findViewById(R.id.nameInput);
//        phoneInput = view.findViewById(R.id.phoneInput);
//        passwordInput = view.findViewById(R.id.passwordInput);
//        studentFields = view.findViewById(R.id.studentFields);
//        teacherFields = view.findViewById(R.id.teacherFields);
//        parentNameInput = view.findViewById(R.id.parentNameInput);
//        parentPhoneInput = view.findViewById(R.id.parentPhoneInput);
//        subjectInput = view.findViewById(R.id.subjectInput);
//        createButton = view.findViewById(R.id.createButton);
//        progressBar = view.findViewById(R.id.progressBar);
//    }
//
//    private void setupSpinners() {
//        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(
//                requireContext(), R.array.user_roles, android.R.layout.simple_spinner_item);
//        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        roleSpinner.setAdapter(roleAdapter);
//
//        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(
//                requireContext(), R.array.class_list, android.R.layout.simple_spinner_item);
//        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        classSpinner.setAdapter(classAdapter);
//    }
//
//    private void setupListeners() {
//        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                updateFieldsVisibility(position);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
//        createButton.setOnClickListener(v -> createUser());
//    }
//
//    private void updateFieldsVisibility(int position) {
//        studentFields.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
//        teacherFields.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
//    }
//
//    private void createUser() {
//        if (!validateInputs()) return;
//        setLoading(true);
//
//        String email = emailInput.getText().toString().trim();
//        String name = nameInput.getText().toString().trim();
//        String phone = phoneInput.getText().toString().trim();
//        String password = passwordInput.getText().toString().trim();
//        String role = roleSpinner.getSelectedItem().toString().toLowerCase();
//
//        User user;
//        switch (role) {
//            case "student":
//                user = new Student(null, email, phone, name, classSpinner.getSelectedItem().toString(),
//                        parentNameInput.getText().toString().trim(), parentPhoneInput.getText().toString().trim());
//                break;
//            case "teacher":
//                user = new Teacher(null, email, phone, name, subjectInput.getText().toString().trim());
//                break;
//            default:
//                user = new Admin(null, email, phone, name, "Staff");
//        }
//
//        firebaseHelper.createUser(user, password, new FirebaseHelper.OnCompleteListener() {
//            @Override
//            public void onSuccess(String registrationId) {
//                setLoading(false);
//                showSuccessDialog(registrationId);
//                clearInputs();
//            }
//            @Override
//            public void onFailure(String error) {
//                setLoading(false);
//                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private boolean validateInputs() {
//        if (TextUtils.isEmpty(emailInput.getText().toString().trim())) {
//            emailInput.setError("Email is required");
//            return false;
//        }
//        if (TextUtils.isEmpty(nameInput.getText().toString().trim())) {
//            nameInput.setError("Name is required");
//            return false;
//        }
//        if (TextUtils.isEmpty(phoneInput.getText().toString().trim())) {
//            phoneInput.setError("Phone is required");
//            return false;
//        }
//        if (TextUtils.isEmpty(passwordInput.getText().toString().trim()) || passwordInput.length() < 6) {
//            passwordInput.setError("Password must be at least 6 characters");
//            return false;
//        }
//        return true;
//    }
//
//    private void setLoading(boolean isLoading) {
//        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
//        createButton.setEnabled(!isLoading);
//    }
//
//    private void showSuccessDialog(String registrationId) {
//        new AlertDialog.Builder(requireContext())
//                .setTitle("User Created Successfully")
//                .setMessage("Registration ID: " + registrationId + "\n\nPlease save this ID for login purposes.")
//                .setPositiveButton("OK", null)
//                .show();
//    }
//
//    private void clearInputs() {
//        emailInput.setText("");
//        nameInput.setText("");
//        phoneInput.setText("");
//        passwordInput.setText("");
//        parentNameInput.setText("");
//        parentPhoneInput.setText("");
//        subjectInput.setText("");
//    }
//}

//
//
//public class CreateUserFragment extends Fragment {
//    // Change from Spinner to AutoCompleteTextView
////    private AutoCompleteTextView roleSpinner, classSpinner;
//    private Spinner roleSpinner, classSpinner;  // Changed to Spinner
//    private EditText emailInput, nameInput, phoneInput, passwordInput;
//    private LinearLayout studentFields, teacherFields;
//    private EditText parentNameInput, parentPhoneInput, subjectInput;
//    private Button createButton;
//    private ProgressBar progressBar;
//    private FirebaseHelper firebaseHelper;
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_create_user, container, false);
//
//        initializeViews(view);
//        setupSpinners();
//        firebaseHelper = new FirebaseHelper(requireContext());
//        return view;
//    }
//
//    private void initializeViews(View view) {
//        // Change the casting from Spinner to AutoCompleteTextView
//        roleSpinner = view.findViewById(R.id.roleSpinner);
//        classSpinner = view.findViewById(R.id.classSpinner);
//        emailInput = view.findViewById(R.id.emailInput);
//        nameInput = view.findViewById(R.id.nameInput);
//        phoneInput = view.findViewById(R.id.phoneInput);
//        passwordInput = view.findViewById(R.id.passwordInput);
//        studentFields = view.findViewById(R.id.studentFields);
//        teacherFields = view.findViewById(R.id.teacherFields);
//        parentNameInput = view.findViewById(R.id.parentNameInput);
//        parentPhoneInput = view.findViewById(R.id.parentPhoneInput);
//        subjectInput = view.findViewById(R.id.subjectInput);
//        createButton = view.findViewById(R.id.createButton);
//        progressBar = view.findViewById(R.id.progressBar);
//    }
//
//    private void setupSpinners() {
//        // Set up role spinner
//        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(
//                requireContext(),
//                R.array.user_roles,
//                android.R.layout.simple_dropdown_item_1line
//        );
//        roleSpinner.setAdapter(roleAdapter);
//
//        // Set up class spinner
//        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(
//                requireContext(),
//                R.array.class_list,
//                android.R.layout.simple_dropdown_item_1line
//        );
//        classSpinner.setAdapter(classAdapter);
//
//        // Set up listeners
//        roleSpinner.setOnItemClickListener((parent, view, position, id) -> {
//            updateFieldsVisibility(position);
//        });
//    }
//
//    private void updateFieldsVisibility(int position) {
//        studentFields.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
//        teacherFields.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
//    }
//
//    // When getting selected values, use:
//    String selectedRole = roleSpinner.getText().toString();
//    String selectedClass = classSpinner.getText().toString();
//}


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sumit.mindspring.R;
import com.sumit.mindspring.models.Admin;
import com.sumit.mindspring.models.Student;
import com.sumit.mindspring.models.Teacher;
import com.sumit.mindspring.models.User;
import com.sumit.mindspring.utils.FirebaseHelper;

import java.util.HashMap;
import java.util.Map;

public class CreateUserFragment extends Fragment {
    private Spinner roleSpinner, classSpinner;
    private EditText emailInput, nameInput, phoneInput, passwordInput;
    private LinearLayout studentFields, teacherFields;
    private EditText parentNameInput, parentPhoneInput, subjectInput;
    private Button createButton;
    private ProgressBar progressBar;
    private FirebaseHelper firebaseHelper;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseHelper = new FirebaseHelper(requireContext());

        initializeViews(view);
        setupSpinners();
        setupListeners();

        return view;
    }

    private void initializeViews(View view) {
        roleSpinner = view.findViewById(R.id.roleSpinner);
        classSpinner = view.findViewById(R.id.classSpinner);
        emailInput = view.findViewById(R.id.emailInput);
        nameInput = view.findViewById(R.id.nameInput);
        phoneInput = view.findViewById(R.id.phoneInput);
        passwordInput = view.findViewById(R.id.passwordInput);

        // Change these lines:
        // studentFields and teacherFields should be finding the LinearLayout inside the MaterialCardView
        studentFields = view.findViewById(R.id.studentFieldsContent);  // This will be a new ID we'll add
        teacherFields = view.findViewById(R.id.teacherFieldsContent);  // This will be a new ID we'll add

        parentNameInput = view.findViewById(R.id.parentNameInput);
        parentPhoneInput = view.findViewById(R.id.parentPhoneInput);
        subjectInput = view.findViewById(R.id.subjectInput);
        createButton = view.findViewById(R.id.createButton);
        progressBar = view.findViewById(R.id.progressBar);
    }
    private void setupSpinners() {
        // Setup role spinner
        String[] roles = {"Student", "Teacher", "Admin"};
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                roles
        );
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);

        // Setup class spinner
        String[] classes = {"Class 1", "Class 2", "Class 3", "Class 4", "Class 5",
                "Class 6", "Class 7", "Class 8", "Class 9", "Class 10",
                "Class 11", "Class 12"};
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                classes
        );
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);
    }

    private void setupListeners() {
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateFieldsVisibility(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        createButton.setOnClickListener(v -> createUser());
    }

    private void updateFieldsVisibility(int position) {
        View studentCard = getView().findViewById(R.id.studentFields);
        View teacherCard = getView().findViewById(R.id.teacherFields);

        studentCard.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        teacherCard.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
    }

    private void createUser() {
        if (!validateInputs()) return;

        setLoading(true);

        String email = emailInput.getText().toString().trim();
        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String role = roleSpinner.getSelectedItem().toString().toLowerCase();

        // Generate a random 5-character registration ID
        String registrationId = generateRegistrationId();

        // Create authentication account
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = task.getResult().getUser().getUid();
                        saveUserToFirestore(uid, email, name, phone, role, registrationId);
                    } else {
                        setLoading(false);
                        Toast.makeText(requireContext(),
                                "Error: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserToFirestore(String uid, String email, String name,
                                     String phone, String role, String registrationId) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("name", name);
        userData.put("phoneNo", phone);
        userData.put("role", role);
        userData.put("registrationId", registrationId);

        // Add role-specific data
        if (role.equals("student")) {
            userData.put("className", classSpinner.getSelectedItem().toString());
            userData.put("parentName", parentNameInput.getText().toString().trim());
            userData.put("parentPhone", parentPhoneInput.getText().toString().trim());
        } else if (role.equals("teacher")) {
            userData.put("subject", subjectInput.getText().toString().trim());
        } else {
            userData.put("position", "Staff");
        }

        // Save to Firestore
        db.collection("users").document(uid)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    setLoading(false);
                    showSuccessDialog(registrationId);
                    clearInputs();
                })
                .addOnFailureListener(e -> {
                    setLoading(false);
                    // Delete the authentication account if Firestore storage fails
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    Toast.makeText(requireContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    private String generateRegistrationId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    private boolean validateInputs() {
        if (TextUtils.isEmpty(emailInput.getText())) {
            emailInput.setError("Email is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput.getText()).matches()) {
            emailInput.setError("Invalid email format");
            return false;
        }
        if (TextUtils.isEmpty(nameInput.getText())) {
            nameInput.setError("Name is required");
            return false;
        }
        if (TextUtils.isEmpty(phoneInput.getText())) {
            phoneInput.setError("Phone number is required");
            return false;
        }
        if (TextUtils.isEmpty(passwordInput.getText())) {
            passwordInput.setError("Password is required");
            return false;
        }
        if (passwordInput.getText().length() < 6) {
            passwordInput.setError("Password must be at least 6 characters");
            return false;
        }

        // Validate role-specific fields
        int selectedRole = roleSpinner.getSelectedItemPosition();
        if (selectedRole == 0) { // Student
            if (TextUtils.isEmpty(parentNameInput.getText())) {
                parentNameInput.setError("Parent's name is required");
                return false;
            }
            if (TextUtils.isEmpty(parentPhoneInput.getText())) {
                parentPhoneInput.setError("Parent's phone is required");
                return false;
            }
        } else if (selectedRole == 1) { // Teacher
            if (TextUtils.isEmpty(subjectInput.getText())) {
                subjectInput.setError("Subject is required");
                return false;
            }
        }

        return true;
    }

    private void showSuccessDialog(String registrationId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("User Created Successfully")
                .setMessage("Registration ID: " + registrationId +
                        "\n\nPlease save this ID for login purposes.")
                .setPositiveButton("OK", null)
                .show();
    }

    private void clearInputs() {
        emailInput.setText("");
        nameInput.setText("");
        phoneInput.setText("");
        passwordInput.setText("");
        parentNameInput.setText("");
        parentPhoneInput.setText("");
        subjectInput.setText("");
        roleSpinner.setSelection(0);
    }

    private void setLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        createButton.setEnabled(!isLoading);
    }
}