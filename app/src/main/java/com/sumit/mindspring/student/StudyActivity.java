package com.sumit.mindspring.student;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.sumit.mindspring.R;

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

    private MaterialCardView ncertCard, formulaCard, pyqCard;
    private BottomNavigationView bottomNav;
    private ImageButton backButton;

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
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(v -> onBackPressed());

        ncertCard.setOnClickListener(v -> openFilesActivity("NCERT"));
        formulaCard.setOnClickListener(v -> openFilesActivity("Formula_sheets"));
        pyqCard.setOnClickListener(v -> openFilesActivity("PYQ"));
    }

    private void openFilesActivity(String section) {
        Intent intent = new Intent(this, FilesActivity.class);
        intent.putExtra("SECTION_NAME", section);
        startActivity(intent);
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
