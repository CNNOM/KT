package com.example.group_project_vstu;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AttendanceDao {
    @Insert
    void insert(Attendance attendance);

    @Query("SELECT * FROM attendance WHERE userId = :userId AND date = :date")
    Attendance getAttendance(int userId, String date);

    @Query("SELECT * FROM attendance WHERE userId = :userId")
    LiveData<List<Attendance>> getAttendanceForUser(int userId);

    @Query("UPDATE attendance SET isPresent = :isPresent WHERE userId = :userId AND date = :date")
    void updateAttendance(int userId, String date, boolean isPresent);
}