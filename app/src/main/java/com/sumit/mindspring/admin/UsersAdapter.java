package com.sumit.mindspring.admin;

//import androidx.recyclerview.widget.RecyclerView;

//
//public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
//    private List<DocumentSnapshot> users;
//    private final OnUserEditListener editListener;
//    private final OnUserDeleteListener deleteListener;
//
//    public interface OnUserEditListener {
//        void onEdit(DocumentSnapshot userDoc);
//    }
//
//    public interface OnUserDeleteListener {
//        void onDelete(DocumentSnapshot userDoc);
//    }
//
//    public UsersAdapter(List<DocumentSnapshot> users, OnUserEditListener editListener,
//                        OnUserDeleteListener deleteListener) {
//        this.users = users;
//        this.editListener = editListener;
//        this.deleteListener = deleteListener;
//    }
//
//    public void updateList(List<DocumentSnapshot> newList) {
//        this.users = newList;
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_user, parent, false);
//        return new UserViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
//        DocumentSnapshot document = users.get(position);
//
//        // Set user details
//        holder.userName.setText(document.getString("name"));
//        holder.userEmail.setText(document.getString("email"));
//        holder.userPhone.setText(document.getString("phone"));
//        holder.userRole.setText(document.getString("role"));
//        holder.registrationId.setText("ID: " + document.getString("registrationId"));
//
//        // Set role-specific details
//        String role = document.getString("role");
//        if ("student".equals(role)) {
//            holder.extraInfo.setText("Class: " + document.getString("className"));
//        } else if ("teacher".equals(role)) {
//            holder.extraInfo.setText("Subject: " + document.getString("subject"));
//        } else {
//            holder.extraInfo.setText("Position: " + document.getString("position"));
//        }
//
//        // Set click listeners
//        holder.editButton.setOnClickListener(v -> editListener.onEdit(document));
//        holder.deleteButton.setOnClickListener(v -> deleteListener.onDelete(document));
//    }
//
//    @Override
//    public int getItemCount() {
//        return users.size();
//    }
//
//    static class UserViewHolder extends RecyclerView.ViewHolder {
//        TextView userName, userEmail, userPhone, userRole, registrationId, extraInfo;
//        Button editButton, deleteButton;
//
//        UserViewHolder(View itemView) {
//            super(itemView);
//            userName = itemView.findViewById(R.id.userName);
//            userEmail = itemView.findViewById(R.id.userEmail);
//            userPhone = itemView.findViewById(R.id.userPhone);
//            userRole = itemView.findViewById(R.id.userRole);
//            registrationId = itemView.findViewById(R.id.registrationId);
//            extraInfo = itemView.findViewById(R.id.extraInfo);
//            editButton = itemView.findViewById(R.id.editButton);
//            deleteButton = itemView.findViewById(R.id.deleteButton);
//        }
//    }
//}
//




import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.sumit.mindspring.R; // Ensure this matches your package structure

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private List<DocumentSnapshot> users;
    private final OnUserEditListener editListener;
    private final OnUserDeleteListener deleteListener;

    // Interfaces for handling edit and delete actions
    public interface OnUserEditListener {
        void onEdit(DocumentSnapshot userDoc);
    }

    public interface OnUserDeleteListener {
        void onDelete(DocumentSnapshot userDoc);
    }

    public UsersAdapter(List<DocumentSnapshot> users, OnUserEditListener editListener,
                        OnUserDeleteListener deleteListener) {
        this.users = users;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    public void updateList(List<DocumentSnapshot> newList) {
        this.users = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        DocumentSnapshot document = users.get(position);

        Log.d("UsersAdapter", "Binding view for user: " + document.getId());

        // Get data safely
        String name = document.getString("name") != null ? document.getString("name") : "N/A";
        String email = document.getString("email") != null ? document.getString("email") : "N/A";
        String phone = document.getString("phone") != null ? document.getString("phone") : "N/A";
        String role = document.getString("role") != null ? document.getString("role").toLowerCase() : "unknown";
        String registrationId = document.getString("registrationId") != null ? document.getString("registrationId") : "N/A";

        // Set text values
        holder.userName.setText(name);
        holder.userEmail.setText("Email: " + email);
        holder.userPhone.setText("Phone: " + phone);
        holder.userRole.setText("Role: " + role);
        holder.registrationId.setText("ID: " + registrationId);

        // Set extra info based on role
        if ("student".equals(role)) {
            String className = document.getString("className") != null ? document.getString("className") : "N/A";
            holder.extraInfo.setText("Class: " + className);
        } else if ("teacher".equals(role)) {
            String subject = document.getString("subject") != null ? document.getString("subject") : "N/A";
            holder.extraInfo.setText("Subject: " + subject);
        } else {
            String positionTitle = document.getString("position") != null ? document.getString("position") : "Staff";
            holder.extraInfo.setText("Position: " + positionTitle);
        }

        // Click listeners
        holder.editButton.setOnClickListener(v -> editListener.onEdit(document));
        holder.deleteButton.setOnClickListener(v -> deleteListener.onDelete(document));
    }

    @Override
    public int getItemCount() {
        Log.d("UsersAdapter", "Item count: " + users.size());
        return users != null ? users.size() : 0;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userEmail, userPhone, userRole, registrationId, extraInfo;
        Button editButton, deleteButton;

        UserViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userEmail = itemView.findViewById(R.id.userEmail);
            userPhone = itemView.findViewById(R.id.userPhone);
            userRole = itemView.findViewById(R.id.userRole);
            registrationId = itemView.findViewById(R.id.registrationId);
            extraInfo = itemView.findViewById(R.id.extraInfo);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}