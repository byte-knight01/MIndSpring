package com.sumit.mindspring;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class select extends AppCompatActivity {

    private ImageButton backButton;
    private LinearLayout studentButton, teacherButton, adminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        // Initialize views
        backButton = findViewById(R.id.backButton);
        studentButton = findViewById(R.id.studentButton);
        teacherButton = findViewById(R.id.teacherButton);
        adminButton = findViewById(R.id.adminButton);

        // Back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will return to the previous activity
                finish();
            }
        });

        // Student button click listener
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming your package name is com.example.yourapp
                // and student activity is in student package
                Intent intent = new Intent();
                intent.setClassName(getPackageName(),
                        getPackageName() + ".student.studentlogin");
                startActivity(intent);
            }
        });

        // Teacher button click listener
        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(getPackageName(),
                        getPackageName() + ".teacher.teacherlogin");
                startActivity(intent);
            }
        });

        // Admin button click listener
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(getPackageName(),
                        getPackageName() + ".admin.adminlogin");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Add any additional logic here if needed
    }
}