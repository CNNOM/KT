package com.example.group_project_vstu;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "attendance",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE))
public class Attendance {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public String date; // Формат: "YYYY-MM-DD"
    public boolean isPresent;

    public Attendance(int userId, String date, boolean isPresent) {
        this.userId = userId;
        this.date = date;
        this.isPresent = isPresent;
    }
}