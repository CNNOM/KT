package com.example.group_project_vstu;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
//                            .fallbackToDestructiveMigration() // Добавьте это, если хотите удалить и создать базу данных заново
                            .build();
                    createAdminUser(INSTANCE);
                }
            }
        }
        return INSTANCE;
    }

    private static void createAdminUser(AppDatabase db) {
        UserDao userDao = db.userDao();
        databaseWriteExecutor.execute(() -> {
            User existingAdmin = userDao.getUserByRole("admin");
            if (existingAdmin == null) {
                Admin admin = new Admin("admin", "admin");
                userDao.insert(admin);
            }
        });
    }
}