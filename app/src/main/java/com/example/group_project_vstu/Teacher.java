package com.example.group_project_vstu;

import androidx.room.Entity;

@Entity(tableName = "users")
public class Teacher extends User {
    public Teacher(String username, String email, String password) {
        super(username, email, password, "teacher");
    }

    // Дополнительные методы и поля для учителя
}