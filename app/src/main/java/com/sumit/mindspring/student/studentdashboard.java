package com.sumit.mindspring.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.sumit.mindspring.R;
import com.sumit.mindspring.select;
import de.hdodenhof.circleimageview.CircleImageView;

public class studentdashboard extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {

    private CardView homeworkCard, doubtsCard, resultCard, analysisCard;
    private ImageView searchIcon, notificationIcon;
    private BottomNavigationView bottomNav;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdashboard);

        // Initialize views
        initializeViews();

        // Set up click listeners
        setupClickListeners();

        // Set up bottom navigation
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(this);

        // Set up navigation drawer
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initializeViews() {
        // Cards
        homeworkCard = findViewById(R.id.homeworkCard);
        doubtsCard = findViewById(R.id.doubtsCard);
        resultCard = findViewById(R.id.resultCard);
        analysisCard = findViewById(R.id.analysisCard);

        // Top bar icons
        searchIcon = findViewById(R.id.searchIcon);
        notificationIcon = findViewById(R.id.notificationIcon);
        profileImage = findViewById(R.id.profileImage);

        // Navigation
        bottomNav = findViewById(R.id.bottomNav);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

//        logoutButton = findViewById(R.id.logoutButton);
    }

    private void setupClickListeners() {
        // Profile image click listener for opening drawer
        profileImage.setOnClickListener(v -> {
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Card click listeners
//        logoutButton.setOnClickListener(v -> showLogoutConfirmationDialog());
        homeworkCard.setOnClickListener(v -> showUpdateToast("Homework feature"));
        doubtsCard.setOnClickListener(v -> showUpdateToast("Doubts feature"));
        resultCard.setOnClickListener(v -> showUpdateToast("Result feature"));
        analysisCard.setOnClickListener(v -> showUpdateToast("Analysis feature"));

        // Top bar icons click listeners
        searchIcon.setOnClickListener(v -> showUpdateToast("Search feature"));
        notificationIcon.setOnClickListener(v -> showUpdateToast("Notifications feature"));
    }

    private void showUpdateToast(String feature) {
        Toast.makeText(this,
                feature + " coming soon in the next update!",
                Toast.LENGTH_SHORT).show();
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> performLogout())
                .setNegativeButton("No", null)
                .show();
    }

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

        // Handle navigation drawer menu items
        if (itemId == R.id.nav_profile) {
            showUpdateToast("Profile");
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (itemId == R.id.nav_settings) {
            showUpdateToast("Settings");
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (itemId == R.id.nav_logout) {
            drawerLayout.closeDrawer(GravityCompat.START);
            showLogoutConfirmationDialog();
            return true;
        }

        // Handle bottom navigation items
        else if (itemId == R.id.nav_home) {
            return true;
        } else if (itemId == R.id.nav_study) {
            startActivity(new Intent(this, StudyActivity.class));
            return true;
        } else if (itemId == R.id.nav_parent) {
            showUpdateToast("Parent portal");
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}