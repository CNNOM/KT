package com.example.group_project_vstu.ui.cyberСoins;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.group_project_vstu.AppDatabase;
import com.example.group_project_vstu.AttendanceDao;
import com.example.group_project_vstu.R;
import com.example.group_project_vstu.User;
import com.example.group_project_vstu.YearData;

import org.threeten.bp.Month;

import java.util.ArrayList;
import java.util.List;

public class CyberCoinsFragment extends Fragment {

    private CyberCoinsViewModel cyberCoinsViewModel;
    private TableLayout tableLayout;
    private Spinner spinnerYear;
    private Spinner spinnerMonth;
    private YearData yearData;
    private AppDatabase db;
    private AttendanceDao attendanceDao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cyberCoinsViewModel = new ViewModelProvider(this).get(CyberCoinsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cyber_coins, container, false);

        tableLayout = root.findViewById(R.id.tableLayout);
        spinnerYear = root.findViewById(R.id.spinnerYear);
        spinnerMonth = root.findViewById(R.id.spinnerMonth);

        db = AppDatabase.getDatabase(getContext());
        attendanceDao = db.attendanceDao();

        yearData = new YearData();
        yearData.populateSaturdays(2023, 2025);

        setupSpinner(spinnerYear, getYears(2023, 2025));
        setupSpinner(spinnerMonth, getMonths());

        addHeaderRow();

        cyberCoinsViewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            populateTable(students);
        });

        return root;
    }

    private void setupSpinner(Spinner spinner, List<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(createItemSelectedListener());
    }

    private AdapterView.OnItemSelectedListener createItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTable();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ничего не делаем
            }
        };
    }

    private List<String> getYears(int startYear, int endYear) {
        List<String> years = new ArrayList<>();
        for (int year = startYear; year <= endYear; year++) {
            years.add(String.valueOf(year));
        }
        return years;
    }

    private List<String> getMonths() {
        List<String> months = new ArrayList<>();
        for (Month month : Month.values()) {
            months.add(month.name());
        }
        return months;
    }

    private void addHeaderRow() {
        HeaderRowBuilder headerRowBuilder = new HeaderRowBuilder(getContext());
        TableRow headerRow = headerRowBuilder.build(yearData.getSaturdays(getSelectedYear(), getSelectedMonth().getValue()));
        tableLayout.removeAllViews();
        tableLayout.addView(headerRow);
    }

    private void populateTable(List<User> students) {
        tableLayout.removeViews(1, tableLayout.getChildCount() - 1);
        CyberCoinsTableRowBuilder tableRowBuilder = new CyberCoinsTableRowBuilder(getContext(), attendanceDao);
        for (User student : students) {
            TableRow row = tableRowBuilder.build(student, yearData.getSaturdays(getSelectedYear(), getSelectedMonth().getValue()));
            tableLayout.addView(row);
        }
    }

    private void updateTable() {
        addHeaderRow();
        cyberCoinsViewModel.getStudents().observe(getViewLifecycleOwner(), this::populateTable);
    }

    private int getSelectedYear() {
        return Integer.parseInt(spinnerYear.getSelectedItem().toString());
    }

    private Month getSelectedMonth() {
        return Month.valueOf(spinnerMonth.getSelectedItem().toString());
    }
}