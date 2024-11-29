package com.example.group_project_vstu;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseMigrations {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Добавляем новое поле points в таблицу attendance
            database.execSQL("ALTER TABLE attendance ADD COLUMN points INTEGER NOT NULL DEFAULT 0");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Здесь можно добавить дополнительные изменения, если они есть
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Здесь можно добавить дополнительные изменения, если они есть
        }
    };
}