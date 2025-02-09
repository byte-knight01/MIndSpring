package com.sumit.mindspring;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        progressBar = findViewById(R.id.progressBar);

        // Start progress bar animation
        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 5; // Increment progress
                handler.post(() -> progressBar.setProgress(progressStatus));
                try {
                    Thread.sleep(100); // Delay to simulate progress
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // After progress is complete, navigate to the next screen
            handler.post(() -> {
                Intent intent = new Intent(Splashscreen.this, get_started.class);
                startActivity(intent);
                finish(); // Finish Splashscreen Activity
            });
        }).start();
    }
}