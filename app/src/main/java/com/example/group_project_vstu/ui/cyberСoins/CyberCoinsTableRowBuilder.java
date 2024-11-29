package com.example.group_project_vstu.ui.cyberСoins;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.group_project_vstu.AppDatabase;
import com.example.group_project_vstu.Attendance;
import com.example.group_project_vstu.AttendanceDao;
import com.example.group_project_vstu.R;
import com.example.group_project_vstu.User;

import org.threeten.bp.Month;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CyberCoinsTableRowBuilder {

    private Context context;
    private AttendanceDao attendanceDao;

    public CyberCoinsTableRowBuilder(Context context, AttendanceDao attendanceDao) {
        this.context = context;
        this.attendanceDao = attendanceDao;
    }

    public TableRow build(User student, List<Integer> saturdays) {
        TableRow row = new TableRow(context);

        TextView usernameTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_textview, row, false);
        usernameTextView.setText(student.getUsername());
        usernameTextView.setTextSize(18);
        usernameTextView.setGravity(android.view.Gravity.CENTER); // Выравнивание по центру
        row.addView(usernameTextView);

        for (int day : saturdays) {
            EditText editText = (EditText) LayoutInflater.from(context).inflate(R.layout.item_edittext, row, false);
            String tag = student.id + "_" + day;
            editText.setTag(tag);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    saveAttendance(student.id, day, s.toString());
                }
            });

            // Загрузка данных в фоновом потоке
            AppDatabase.databaseWriteExecutor.execute(() -> {
                Attendance attendance = getAttendanceForStudent(student.id, day);
                if (attendance != null) {
                    editText.post(() -> editText.setText(String.valueOf(attendance.getPoints())));
                }
            });

            row.addView(editText);
        }

        return row;
    }

    private void saveAttendance(int userId, int day, String points) {
        if (points.isEmpty()) {
            // Если строка пустая, проигнорируем сохранение или установим значение по умолчанию
            return;
        }

        String currentDate = getDateForDay(day);

        AppDatabase.databaseWriteExecutor.execute(() -> {
            Attendance attendance = attendanceDao.getAttendance(userId, currentDate);
            if (attendance == null) {
                attendance = new Attendance(userId, currentDate, false);
                attendance.setPoints(Integer.parseInt(points));
                attendanceDao.insert(attendance);
            } else {
                attendance.setPoints(Integer.parseInt(points));
                attendanceDao.updatePoints(userId, currentDate, attendance.getPoints());
            }
        });
    }

    private Attendance getAttendanceForStudent(int userId, int day) {
        String currentDate = getDateForDay(day);

        Future<Attendance> future = AppDatabase.databaseWriteExecutor.submit(() -> {
            return attendanceDao.getAttendance(userId, currentDate);
        });

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
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
        // Этот метод должен быть реализован в CyberCoinsFragment
        // и передаваться через конструктор CyberCoinsTableRowBuilder
        return 0; // Заглушка, нужно заменить на реальную реализацию
    }

    private Month getSelectedMonth() {
        // Этот метод должен быть реализован в CyberCoinsFragment
        // и передаваться через конструктор CyberCoinsTableRowBuilder
        return Month.JANUARY; // Заглушка, нужно заменить на реальную реализацию
    }
}