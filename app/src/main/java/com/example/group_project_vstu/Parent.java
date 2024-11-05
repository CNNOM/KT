package com.example.group_project_vstu;

import androidx.room.Entity;

@Entity(tableName = "users")
public class Parent extends User {
    public Parent(String username, String email, String password) {
        super(username, email, password, "parent");
    }

    // Дополнительные методы и поля для родителя
}