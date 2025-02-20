package com.sumit.mindspring;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sumit.mindspring.admin.adminlogin;
import com.sumit.mindspring.teacher.teacherlogin;

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

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(select.this, adminlogin.class));
//                Toast.makeText(v.getContext(), "Please contact administrator", Toast.LENGTH_SHORT).show();
            }
        });

        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(select.this, teacherlogin.class));
//
//                Toast.makeText(v.getContext(), "Please contact administrator", Toast.LENGTH_SHORT).show();
            }
        });

    }}

//public class select extends AppCompatActivity {
//    private CardView adminCard, teacherCard, studentCard;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select);
//
//        adminCard = findViewById(R.id.adminCard);
//        teacherCard = findViewById(R.id.teacherCard);
//        studentCard = findViewById(R.id.studentCard);
//
//        adminCard.setOnClickListener(v -> {
//            startActivity(new Intent(select.this, adminlogin.class));
//        });
//
//        teacherCard.setOnClickListener(v -> {
//            startActivity(new Intent(select.this, teacherlogin.class));
//        });
//
//        studentCard.setOnClickListener(v -> {
//            startActivity(new Intent(select.this, studentlogin.class));
//        });
//    }
//}