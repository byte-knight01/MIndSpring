package com.sumit.mindspring.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sumit.mindspring.R;

public class studentdashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private CardView homeworkCard, doubtsCard, resultCard, analysisCard;
    private ImageView searchIcon, notificationIcon;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdashboard);

        // Initialize views
        initializeViews();

        // Set up click listeners
        setupClickListeners();

        // Set up bottom navigation
        bottomNav.setSelectedItemId(R.id.nav_home); // Set home as default
        bottomNav.setOnNavigationItemSelectedListener(this);
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

        // Bottom navigation
        bottomNav = findViewById(R.id.bottomNav);
    }

    private void setupClickListeners() {
        // Card click listeners
        homeworkCard.setOnClickListener(v ->
                showUpdateToast("Homework feature"));

        doubtsCard.setOnClickListener(v ->
                showUpdateToast("Doubts feature"));

        resultCard.setOnClickListener(v ->
                showUpdateToast("Result feature"));

        analysisCard.setOnClickListener(v ->
                showUpdateToast("Analysis feature"));

        // Top bar icons click listeners
        searchIcon.setOnClickListener(v ->
                showUpdateToast("Search feature"));

        notificationIcon.setOnClickListener(v ->
                showUpdateToast("Notifications feature"));
    }

    private void showUpdateToast(String feature) {
        Toast.makeText(this,
                feature + " coming soon in the next update!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            return true;
        }
        else if (itemId == R.id.nav_study) {
            // Launch Study Activity
            startActivity(new Intent(this, StudyActivity.class));
            return true;
        }
        else if (itemId == R.id.nav_parent) {
            showUpdateToast("Parent portal");
            return true;
        }

        return false;
    }
}