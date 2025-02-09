package com.sumit.mindspring.student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sumit.mindspring.R;
import java.util.List;

class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {
    private List<StorageFile> files;
    private final OnFileClickListener listener;

    public FileAdapter(List<StorageFile> files, OnFileClickListener listener) {
        this.files = files;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_file, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        StorageFile file = files.get(position);
        holder.fileNameText.setText(file.getName());
        holder.itemView.setOnClickListener(v -> listener.onFileClick(file));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void updateFiles(List<StorageFile> newFiles) {
        this.files = newFiles;
        notifyDataSetChanged();
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        TextView fileNameText;

        FileViewHolder(View itemView) {
            super(itemView);
            fileNameText = itemView.findViewById(R.id.fileNameText);
        }
    }

    interface OnFileClickListener {
        void onFileClick(StorageFile file);
    }
}