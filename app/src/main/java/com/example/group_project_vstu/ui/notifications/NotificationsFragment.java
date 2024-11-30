package com.example.group_project_vstu.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group_project_vstu.R;
import com.example.group_project_vstu.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment implements FileAdapter.OnFileClickListener {

    private FragmentNotificationsBinding binding;
    private NotificationsViewModel notificationsViewModel;

    private List<String> fileNames;
    private List<String> fileContents;
    private List<Boolean> fileContentVisibility;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Загрузка всех файлов из res/raw
        loadFileNames();

        // Создание списка файлов
        fileContents = new ArrayList<>(fileNames.size());
        fileContentVisibility = new ArrayList<>(fileNames.size());
        for (int i = 0; i < fileNames.size(); i++) {
            fileContents.add("");
            fileContentVisibility.add(false);
        }

        RecyclerView fileRecyclerView = binding.fileRecyclerView;
        fileRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        FileAdapter adapter = new FileAdapter(fileNames, fileContents, fileContentVisibility, this);
        fileRecyclerView.setAdapter(adapter);

        return root;
    }

    private void loadFileNames() {
        fileNames = new ArrayList<>();
        String[] rawResources = requireContext().getResources().getStringArray(R.array.raw_files);
        for (String resourceName : rawResources) {
            fileNames.add(resourceName);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onFileClick(int position) {
        if (!fileContentVisibility.get(position)) {
            notificationsViewModel.loadFileContent(requireContext(), fileNames.get(position));
            fileContents.set(position, notificationsViewModel.getFileContent().getValue());
        }
        ((FileAdapter) binding.fileRecyclerView.getAdapter()).toggleContentVisibility(position);
    }
}