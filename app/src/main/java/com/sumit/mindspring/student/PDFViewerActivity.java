package com.sumit.mindspring.student;
// PDFViewerActivity.java

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sumit.mindspring.R;

import java.io.File;

public class PDFViewerActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private PDFView pdfView;
    private ProgressBar progressBar;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        // Initialize views
        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.progressBar);
        ImageButton backButton = findViewById(R.id.backButton);

        // Set click listener for back button
        backButton.setOnClickListener(v -> onBackPressed());

        // Get file path from intent
        filePath = getIntent().getStringExtra("FILE_PATH");
        if (filePath == null) {
            Toast.makeText(this, "Error: File path not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load the PDF
        loadPDFFromFirebase();
    }

    private void loadPDFFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);

        StorageReference fileRef = FirebaseStorage.getInstance().getReference(filePath);
        File localFile = new File(getCacheDir(), "temp.pdf");

        fileRef.getFile(localFile)
                .addOnSuccessListener(taskSnapshot -> {
                    displayPDF(localFile);
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(PDFViewerActivity.this,
                            "Error loading PDF: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    private void displayPDF(File pdfFile) {
        pdfView.fromFile(pdfFile)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .onPageChange(this)
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        setTitle(String.format("%s %s / %s", filePath, page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {
        progressBar.setVisibility(View.GONE);
    }
}