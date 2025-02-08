package com.sumit.mindspring.student;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.sumit.mindspring.R;
import com.sumit.mindspring.select;

//public class StudyActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
//
//    private MaterialCardView ncertCard, formulaCard, pyqCard;
//    private BottomNavigationView bottomNav;
//    private ImageButton backButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_study);
//
//        initializeViews();
//        setupClickListeners();
//
//        // Set study as selected in bottom nav
//        bottomNav.setSelectedItemId(R.id.nav_study);
//        bottomNav.setOnNavigationItemSelectedListener(this);
//    }
//
//    private void initializeViews() {
//        ncertCard = findViewById(R.id.ncertCard);
//        formulaCard = findViewById(R.id.formulaCard);
//        pyqCard = findViewById(R.id.pyqCard);
//        bottomNav = findViewById(R.id.bottomNav);
//        backButton = findViewById(R.id.backButton);
//    }
//
//    private void setupClickListeners() {
//        backButton.setOnClickListener(v -> onBackPressed());
//
//        ncertCard.setOnClickListener(v ->
//                showFeatureToast("NCERT"));
//
//        formulaCard.setOnClickListener(v ->
//                showFeatureToast("Formula sheets"));
//
//        pyqCard.setOnClickListener(v ->
//                showFeatureToast("PYQ"));
//    }
//
//    private void showFeatureToast(String feature) {
//        Toast.makeText(this,
//                feature + " section coming soon!",
//                Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int itemId = item.getItemId();
//
//        if (itemId == R.id.nav_home) {
//            finish(); // Go back to MainActivity
//            return true;
//        }
//        else if (itemId == R.id.nav_study) {
//            return true; // Already in Study
//        }
//        else if (itemId == R.id.nav_parent) {
//            Toast.makeText(this, "Parent portal coming soon!", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return false;
//    }
//}

public class StudyActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private MaterialCardView ncertCard, formulaCard, pyqCard, jeeMainsPyqCard, jeeAdvancedPyqCard, neetPyqCard;
    private BottomNavigationView bottomNav;
    private ImageButton backButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        initializeViews();
        setupClickListeners();

        // Set study as selected in bottom nav
        bottomNav.setSelectedItemId(R.id.nav_study);
        bottomNav.setOnNavigationItemSelectedListener(this);
    }

    private void initializeViews() {
        ncertCard = findViewById(R.id.ncertCard);
        formulaCard = findViewById(R.id.formulaCard);
        pyqCard = findViewById(R.id.pyqCard);
        bottomNav = findViewById(R.id.bottomNav);
        backButton = findViewById(R.id.backButton);
        jeeMainsPyqCard = findViewById(R.id.jeeMainsPyqCard);
        jeeAdvancedPyqCard = findViewById(R.id.jeeAdvancedPyqCard);
        neetPyqCard = findViewById(R.id.neetPyqCard);
        logoutButton = findViewById(R.id.logoutButton);
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(v -> onBackPressed());
        logoutButton.setOnClickListener(v -> showLogoutConfirmationDialog());


        ncertCard.setOnClickListener(v -> openFilesActivity("NCERT"));
        formulaCard.setOnClickListener(v -> openFilesActivity("Formula_sheets"));
        pyqCard.setOnClickListener(v -> openFilesActivity("PYQ"));
        jeeMainsPyqCard.setOnClickListener(v -> openFilesActivity("JEE_Mains_PYQ"));
        jeeAdvancedPyqCard.setOnClickListener(v -> openFilesActivity("JEE_Advanced_PYQ"));
        neetPyqCard.setOnClickListener(v -> openFilesActivity("NEET_PYQ"));
    }

    private void openFilesActivity(String section) {
        Intent intent = new Intent(this, FilesActivity.class);
        intent.putExtra("SECTION_NAME", section);
        startActivity(intent);
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> performLogout())
                .setNegativeButton("No", null)
                .show();
    }

//    private void performLogout() {
//        // Clear user session (assuming you're using SharedPreferences)
//        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//        preferences.edit().clear().apply();
//
//        // Navigate to login screen
//        Intent intent = new Intent(this, select.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
//    }


    private void performLogout() {
        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();

        // Clear any local preferences if needed
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        preferences.edit().clear().apply();

        // Navigate to select activity
        Intent intent = new Intent(this, select.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            finish(); // Go back to MainActivity
            return true;
        }
        else if (itemId == R.id.nav_study) {
            return true; // Already in Study
        }
        else if (itemId == R.id.nav_parent) {
            Toast.makeText(this, "Parent portal coming soon!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
