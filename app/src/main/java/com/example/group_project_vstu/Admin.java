package com.example.group_project_vstu;

import androidx.room.Entity;

@Entity(tableName = "users")
public class Admin extends User {
    public Admin(String username, String password) {
        super(username, "", password, "admin");
    }

    // Дополнительные методы и поля для админа
}