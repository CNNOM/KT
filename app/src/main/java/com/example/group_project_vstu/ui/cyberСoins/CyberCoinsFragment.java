package com.example.group_project_vstu.ui.cyberСoins;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.group_project_vstu.databinding.FragmentCyberCoinsBinding;

public class CyberCoinsFragment extends Fragment {

    private FragmentCyberCoinsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CyberCoinsViewModel cyberCoinsViewModel =
                new ViewModelProvider(this).get(CyberCoinsViewModel.class);

        binding = FragmentCyberCoinsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCyberCoins;
        cyberCoinsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}