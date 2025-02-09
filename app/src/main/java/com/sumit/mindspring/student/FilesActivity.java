package com.sumit.mindspring.student;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sumit.mindspring.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesActivity extends AppCompatActivity {
    private RecyclerView filesRecyclerView;
    private ProgressBar progressBar;
    private TextView emptyView, titleText;
    private FileAdapter adapter;
    private FirebaseStorage storage;
    private String sectionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);

        sectionName = getIntent().getStringExtra("SECTION_NAME");
        if (sectionName == null) {
            finish();
            return;
        }

        initializeViews();
        setupFirebase();
        loadFiles();
    }

    private void initializeViews() {
        filesRecyclerView = findViewById(R.id.filesRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        emptyView = findViewById(R.id.emptyView);
        titleText = findViewById(R.id.titleText);
        ImageButton backButton = findViewById(R.id.backButton);

        titleText.setText(sectionName);
        backButton.setOnClickListener(v -> onBackPressed());

        filesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FileAdapter(new ArrayList<>(), this::handleFileClick);
        filesRecyclerView.setAdapter(adapter);
    }

    private void setupFirebase() {
        storage = FirebaseStorage.getInstance();
    }

    private void loadFiles() {
        progressBar.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

        StorageReference folderRef = storage.getReference().child(sectionName);
        folderRef.listAll()
                .addOnSuccessListener(listResult -> {
                    List<StorageFile> files = new ArrayList<>();
                    for (StorageReference item : listResult.getItems()) {
                        files.add(new StorageFile(item.getName(), item.getPath()));
                    }

                    progressBar.setVisibility(View.GONE);
                    if (files.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        adapter.updateFiles(files);
                    }
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error loading files: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

//    opening a new pdfviewer activity
    private void handleFileClick(StorageFile file) {
        Intent intent = new Intent(this, PDFViewerActivity.class);
        intent.putExtra("FILE_PATH", file.getPath());
        startActivity(intent);
    }

    private void openPDFFile(File file) {
        Uri uri = FileProvider.getUriForFile(this,
                getApplicationContext().getPackageName() + ".provider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Check if there's an app that can handle PDF files
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this,
                    "Please install a PDF reader application",
                    Toast.LENGTH_LONG).show();
        }
    }
}