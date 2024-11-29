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

    // Добавляем метод для обновления баллов
    @Query("UPDATE attendance SET points = :points WHERE userId = :userId AND date = :date")
    void updatePoints(int userId, String date, int points);

    // Добавляем метод для обновления состояния и баллов одновременно
    @Query("UPDATE attendance SET isPresent = :isPresent, points = :points WHERE userId = :userId AND date = :date")
    void updateAttendanceAndPoints(int userId, String date, boolean isPresent, int points);
}