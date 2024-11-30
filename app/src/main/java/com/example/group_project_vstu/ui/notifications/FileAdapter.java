package com.example.group_project_vstu.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group_project_vstu.R;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private final List<String> fileNames;
    private final List<String> fileContents;
    private final List<Boolean> fileContentVisibility;
    private final OnFileClickListener onFileClickListener;

    public FileAdapter(List<String> fileNames, List<String> fileContents, List<Boolean> fileContentVisibility, OnFileClickListener onFileClickListener) {
        this.fileNames = fileNames;
        this.fileContents = fileContents;
        this.fileContentVisibility = fileContentVisibility;
        this.onFileClickListener = onFileClickListener;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        holder.fileNameTextView.setText(fileNames.get(position));
        holder.fileContentTextView.setText(fileContents.get(position));
        holder.fileContentTextView.setVisibility(fileContentVisibility.get(position) ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(v -> onFileClickListener.onFileClick(position));
    }

    @Override
    public int getItemCount() {
        return fileNames.size();
    }

    public void toggleContentVisibility(int position) {
        fileContentVisibility.set(position, !fileContentVisibility.get(position));
        notifyItemChanged(position);
    }

    public interface OnFileClickListener {
        void onFileClick(int position);
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        TextView fileNameTextView;
        TextView fileContentTextView;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.fileNameTextView);
            fileContentTextView = itemView.findViewById(R.id.fileContentTextView);
        }
    }
}