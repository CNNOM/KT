package com.example.group_project_vstu.ui.cyberСoins;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.group_project_vstu.AppDatabase;
import com.example.group_project_vstu.Attendance;
import com.example.group_project_vstu.AttendanceDao;
import com.example.group_project_vstu.CheckBoxState;
import com.example.group_project_vstu.R;
import com.example.group_project_vstu.User;

import org.threeten.bp.Month;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TableRowBuilder {

    private Context context;
    private AttendanceDao attendanceDao;
    private Map<String, CheckBoxState> checkBoxStateMap;

    public TableRowBuilder(Context context, AttendanceDao attendanceDao, Map<String, CheckBoxState> checkBoxStateMap) {
        this.context = context;
        this.attendanceDao = attendanceDao;
        this.checkBoxStateMap = checkBoxStateMap;
    }

    public TableRow build(User student, List<Integer> saturdays) {
        TableRow row = new TableRow(context);

        TextView usernameTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_textview, row, false);
        usernameTextView.setText(student.getUsername());
        usernameTextView.setTextSize(18);
        usernameTextView.setGravity(android.view.Gravity.CENTER); // Выравнивание по центру
        row.addView(usernameTextView);

        for (int day : saturdays) {
            CheckBox checkBox = (CheckBox) LayoutInflater.from(context).inflate(R.layout.item_checkbox, row, false);
            String tag = student.id + "_" + day;
            checkBox.setTag(tag);
            checkBox.setOnClickListener(v -> saveAttendance(student.id, day, checkBox.isChecked()));

            CheckBoxState checkBoxState = checkBoxStateMap.get(tag);
            if (checkBoxState != null) {
                checkBox.setChecked(checkBoxState.isChecked());
            } else {
                Attendance attendance = getAttendanceForStudent(student.id, day);
                boolean isChecked = attendance != null ? attendance.isPresent : false;
                checkBoxState = new CheckBoxState(student.id, day, isChecked);
                checkBoxStateMap.put(tag, checkBoxState);
                checkBox.setChecked(isChecked);
            }

            row.addView(checkBox);
        }

        return row;
    }

    private void saveAttendance(int userId, int day, boolean isPresent) {
        String currentDate = getDateForDay(day);

        AppDatabase.databaseWriteExecutor.execute(() -> {
            Attendance attendance = attendanceDao.getAttendance(userId, currentDate);
            if (attendance == null) {
                Log.d("Attendance", "Inserting new attendance for user " + userId + " on " + currentDate + " for day " + day);
                attendance = new Attendance(userId, currentDate, isPresent);
                attendanceDao.insert(attendance);
            } else {
                Log.d("Attendance", "Updating attendance for user " + userId + " on " + currentDate + " for day " + day);
                attendanceDao.updateAttendance(userId, currentDate, isPresent);
            }

            // Обновляем состояние в кэше
            String tag = userId + "_" + day;
            CheckBoxState checkBoxState = checkBoxStateMap.get(tag);
            if (checkBoxState != null) {
                checkBoxState.setChecked(isPresent);
            }
        });
    }

    private Attendance getAttendanceForStudent(int userId, int day) {
        String currentDate = getDateForDay(day);

        Future<Attendance> future = AppDatabase.databaseWriteExecutor.submit(() -> {
            return attendanceDao.getAttendance(userId, currentDate);
        });

        try {
            Attendance attendance = future.get();
            if (attendance != null) {
                Log.d("Attendance", "Loaded attendance for user " + userId + " on " + currentDate + " for day " + day + ": " + attendance.isPresent);
            } else {
                Log.d("Attendance", "No attendance found for user " + userId + " on " + currentDate + " for day " + day);
            }
            return attendance;
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

    private int getSelectedYear() {
        // Этот метод должен быть реализован в DashboardFragment
        // и передаваться через конструктор TableRowBuilder
        return 0; // Заглушка, нужно заменить на реальную реализацию
    }

    private Month getSelectedMonth() {
        // Этот метод должен быть реализован в DashboardFragment
        // и передаваться через конструктор TableRowBuilder
        return Month.JANUARY; // Заглушка, нужно заменить на реальную реализацию
    }
}