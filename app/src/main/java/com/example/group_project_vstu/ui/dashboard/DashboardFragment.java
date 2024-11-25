package com.example.group_project_vstu.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
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
import com.example.group_project_vstu.Attendance;
import com.example.group_project_vstu.AttendanceDao;
import com.example.group_project_vstu.CheckBoxState;
import com.example.group_project_vstu.R;
import com.example.group_project_vstu.User;
import com.example.group_project_vstu.YearData;

import org.threeten.bp.Month;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private TableLayout tableLayout;
    private Spinner spinnerYear;
    private Spinner spinnerMonth;
    private YearData yearData;
    private AppDatabase db;
    private AttendanceDao attendanceDao;
    private Map<String, CheckBoxState> checkBoxStateMap = new HashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

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

        dashboardViewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            populateTable(students);
            updateCheckBoxStates(students);
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
        TableRowBuilder tableRowBuilder = new TableRowBuilder(getContext(), attendanceDao, checkBoxStateMap);
        for (User student : students) {
            TableRow row = tableRowBuilder.build(student, yearData.getSaturdays(getSelectedYear(), getSelectedMonth().getValue()));
            tableLayout.addView(row);
        }
    }

    private void updateCheckBoxStates(List<User> students) {
        for (User student : students) {
            for (int day : yearData.getSaturdays(getSelectedYear(), getSelectedMonth().getValue())) {
                String tag = student.id + "_" + day;
                CheckBoxState checkBoxState = checkBoxStateMap.get(tag);
                if (checkBoxState == null) {
                    Attendance attendance = getAttendanceForStudent(student.id, day);
                    boolean isChecked = attendance != null ? attendance.isPresent : false;
                    checkBoxState = new CheckBoxState(student.id, day, isChecked);
                    checkBoxStateMap.put(tag, checkBoxState);
                }
            }
        }
    }

    private Attendance getAttendanceForStudent(int userId, int day) {
        String currentDate = getDateForDay(day);

        Future<Attendance> future = AppDatabase.databaseWriteExecutor.submit(() -> {
            return attendanceDao.getAttendance(userId, currentDate);
        });

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            Log.e("Attendance", "Error loading attendance for user " + userId + " on " + currentDate + " for day " + day, e);
            return null;
        }
    }

    private String getDateForDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getSelectedYear());
        calendar.set(Calendar.MONTH, getSelectedMonth().getValue() - 1); // Месяцы в Calendar начинаются с 0
        calendar.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    private void updateTable() {
        addHeaderRow();
        dashboardViewModel.getStudents().observe(getViewLifecycleOwner(), this::populateTable);
    }

    private int getSelectedYear() {
        return Integer.parseInt(spinnerYear.getSelectedItem().toString());
    }

    private Month getSelectedMonth() {
        return Month.valueOf(spinnerMonth.getSelectedItem().toString());
    }
}