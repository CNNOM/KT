package com.example.group_project_vstu.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.group_project_vstu.R;
import com.example.group_project_vstu.User;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private TableLayout tableLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tableLayout = root.findViewById(R.id.tableLayout);

        dashboardViewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            populateTable(students);
        });

        return root;
    }

    private void populateTable(List<User> students) {
        for (User student : students) {
            TableRow row = new TableRow(getContext());

            TextView usernameTextView = new TextView(getContext());
            usernameTextView.setText(student.getUsername());
            usernameTextView.setPadding(8, 8, 8, 8);
            row.addView(usernameTextView);

            CheckBox checkBoxDay7 = new CheckBox(getContext());
            checkBoxDay7.setPadding(8, 8, 8, 8);
            row.addView(checkBoxDay7);

            CheckBox checkBoxDay14 = new CheckBox(getContext());
            checkBoxDay14.setPadding(8, 8, 8, 8);
            row.addView(checkBoxDay14);

            CheckBox checkBoxDay21 = new CheckBox(getContext());
            checkBoxDay21.setPadding(8, 8, 8, 8);
            row.addView(checkBoxDay21);

            CheckBox checkBoxDay28 = new CheckBox(getContext());
            checkBoxDay28.setPadding(8, 8, 8, 8);
            row.addView(checkBoxDay28);

            tableLayout.addView(row);
        }
    }
}