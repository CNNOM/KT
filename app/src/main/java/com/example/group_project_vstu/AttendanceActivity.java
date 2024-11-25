package com.example.group_project_vstu;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

    private AppDatabase db;
    private UserDao userDao;
    private AttendanceDao attendanceDao;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        db = AppDatabase.getDatabase(getApplicationContext());
        userDao = db.userDao();
        attendanceDao = db.attendanceDao();

        tableLayout = findViewById(R.id.tableLayout);

        loadStudents();
    }

    private void loadStudents() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<User> students = userDao.getStudents();
            runOnUiThread(() -> populateTable(students));
        });
    }

    private void populateTable(List<User> students) {
        tableLayout.removeAllViews();

        for (User student : students) {
            TableRow row = new TableRow(this);

            TextView textViewUsername = new TextView(this);
            textViewUsername.setText(student.getUsername());
            row.addView(textViewUsername);

            CheckBox checkBox = new CheckBox(this);
            checkBox.setTag(student.id);
            checkBox.setOnClickListener(v -> saveAttendance(student.id, checkBox.isChecked()));

            Attendance attendance = getAttendanceForStudent(student.id);
            if (attendance != null) {
                checkBox.setChecked(attendance.isPresent);
            }

            row.addView(checkBox);
            tableLayout.addView(row);
        }
    }

    private Attendance getAttendanceForStudent(int userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        return attendanceDao.getAttendance(userId, currentDate);
    }

    private void saveAttendance(int userId, boolean isPresent) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());

        AppDatabase.databaseWriteExecutor.execute(() -> {
            Attendance attendance = attendanceDao.getAttendance(userId, currentDate);
            if (attendance == null) {
                attendance = new Attendance(userId, currentDate, isPresent);
                attendanceDao.insert(attendance);
            } else {
                attendanceDao.updateAttendance(userId, currentDate, isPresent);
            }
        });
    }
}