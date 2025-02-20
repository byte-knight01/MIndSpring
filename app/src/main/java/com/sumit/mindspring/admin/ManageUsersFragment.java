package com.sumit.mindspring.admin;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sumit.mindspring.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.SearchView;

public class ManageUsersFragment extends Fragment {
    private RecyclerView usersRecyclerView;
    private Spinner filterSpinner;
    private SearchView searchView;  // Make sure it's androidx.appcompat.widget.SearchView

    private UsersAdapter adapter;
    private FirebaseFirestore db;
    private List<DocumentSnapshot> usersList;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_users, container, false);

        // Initialize variables
        db = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();

        initializeViews(view);
        setupRecyclerView();
        setupFilterSpinner();
        setupSearchView();

        // Load initial data
        loadUsers("all");

        return view;
    }

    private void initializeViews(View view) {
        usersRecyclerView = view.findViewById(R.id.usersRecyclerView);
        filterSpinner = view.findViewById(R.id.filterSpinner);
        searchView = view.findViewById(R.id.searchView);
        progressBar = view.findViewById(R.id.progressBar);
        db = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();

        Log.d("ManageUsers", "Views initialized");

    }

    private void setupRecyclerView() {
        adapter = new UsersAdapter(usersList, this::showEditDialog, this::showDeleteConfirmation);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        usersRecyclerView.setHasFixedSize(true);

        usersRecyclerView.setAdapter(adapter);
    }

    private void setupFilterSpinner() {
        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.user_filters,
                android.R.layout.simple_spinner_item
        );
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(filterAdapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFilter = parent.getItemAtPosition(position).toString().toLowerCase();
                loadUsers(selectedFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterUsers(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterUsers(newText);
                return true;
            }
        });
    }

//    private void loadUsers(String filter) {
//        setLoading(true);
//        Query query = db.collection("users");
//
//        // Add logging
//        Log.d("ManageUsers", "Loading users with filter: " + filter);
//
//        if (!filter.equals("all")) {
//            query = query.whereEqualTo("role", filter);
//        }
//
//        query.get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    usersList.clear();
//                    usersList.addAll(queryDocumentSnapshots.getDocuments());
//                    // Add logging
//                    Log.d("ManageUsers", "Users loaded: " + usersList.size());
//                    adapter.notifyDataSetChanged();
//                    setLoading(false);
//                })
//                .addOnFailureListener(e -> {
//                    // Add logging
//                    Log.e("ManageUsers", "Error loading users: " + e.getMessage());
//                    Toast.makeText(requireContext(),
//                            "Error loading users: " + e.getMessage(),
//                            Toast.LENGTH_SHORT).show();
//                    setLoading(false);
//                });
//    }

    private void loadUsers(String filter) {
        setLoading(true);
        Query query = db.collection("users");

        Log.d("ManageUsers", "Starting to load users with filter: " + filter);

        if (!filter.equals("all")) {
            query = query.whereEqualTo("role", filter.toLowerCase());
        }

        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        usersList.clear();
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Log.d("ManageUsers", "User found: " + document.getString("name") +
                                    " Role: " + document.getString("role"));
                            usersList.add(document);
                        }
                        adapter.notifyDataSetChanged();
                        Log.d("ManageUsers", "Total users loaded: " + usersList.size());
                    } else {
                        Log.d("ManageUsers", "No users found in database");
                    }
                    setLoading(false);
                })
                .addOnFailureListener(e -> {
                    Log.e("ManageUsers", "Error loading users: " + e.getMessage());
                    setLoading(false);
                    Toast.makeText(requireContext(),
                            "Error loading users: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }



    private void filterUsers(String query) {
        if (TextUtils.isEmpty(query)) {
            adapter.updateList(usersList);
            return;
        }

        List<DocumentSnapshot> filteredList = new ArrayList<>();
        for (DocumentSnapshot doc : usersList) {
            String name = doc.getString("name");
            String email = doc.getString("email");
            String regId = doc.getString("registrationId");

            if ((name != null && name.toLowerCase().contains(query.toLowerCase())) ||
                    (email != null && email.toLowerCase().contains(query.toLowerCase())) ||
                    (regId != null && regId.toLowerCase().contains(query.toLowerCase()))) {
                filteredList.add(doc);
            }
        }
        adapter.updateList(filteredList);
    }

    private void showEditDialog(DocumentSnapshot userDoc) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_edit_user);
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        EditText nameInput = dialog.findViewById(R.id.nameInput);
        EditText phoneInput = dialog.findViewById(R.id.phoneInput);
        Button saveButton = dialog.findViewById(R.id.saveButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);

        // Set current values
        nameInput.setText(userDoc.getString("name"));
        phoneInput.setText(userDoc.getString("phone"));

        saveButton.setOnClickListener(v -> {
            Map<String, Object> updates = new HashMap<>();
            updates.put("name", nameInput.getText().toString().trim());
            updates.put("phone", phoneInput.getText().toString().trim());

            userDoc.getReference().update(updates)
                    .addOnSuccessListener(aVoid -> {
                        dialog.dismiss();
                        loadUsers(filterSpinner.getSelectedItem().toString().toLowerCase());
                        Toast.makeText(requireContext(),
                                "User updated successfully",
                                Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(),
                                "Error updating user: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    });
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showDeleteConfirmation(DocumentSnapshot userDoc) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    userDoc.getReference().delete()
                            .addOnSuccessListener(aVoid -> {
                                loadUsers(filterSpinner.getSelectedItem().toString().toLowerCase());
                                Toast.makeText(requireContext(),
                                        "User deleted successfully",
                                        Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(requireContext(),
                                        "Error deleting user: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void setLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}