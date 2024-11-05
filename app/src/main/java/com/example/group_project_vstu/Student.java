package com.example.group_project_vstu;

import androidx.room.Entity;

@Entity(tableName = "users")
public class Student extends User {
    public Student(String username, String email, String password) {
        super(username, email, password, "student");
    }

    // Дополнительные методы и поля для ученика
}