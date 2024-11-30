package com.example.group_project_vstu.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.group_project_vstu.R;
import com.example.group_project_vstu.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private NotificationsViewModel notificationsViewModel;

    private final int[] fileResources = {R.raw.file1, R.raw.file2, R.raw.file3};
    private final String[] fileNames = {"file1.txt", "file2.txt", "file3.txt"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Обработка изменения содержимого файла
        notificationsViewModel.getFileContent().observe(getViewLifecycleOwner(), textView::setText);

        // Создание списка файлов
        ListView fileListView = binding.fileListView;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, fileNames);
        fileListView.setAdapter(adapter);

        // Обработка выбора файла
        fileListView.setOnItemClickListener((parent, view, position, id) -> {
            notificationsViewModel.loadFileContent(requireContext(), fileResources[position]);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}