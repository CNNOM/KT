package com.example.group_project_vstu.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.group_project_vstu.AppDatabase;
import com.example.group_project_vstu.User;
import com.example.group_project_vstu.UserDao;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<User>> mUsers;
    private final UserDao userDao;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mUsers = new MutableLiveData<>();
        mText.setValue("Привет получилось");

        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();

        loadUsers();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<User>> getUsers() {
        return mUsers;
    }

    private void loadUsers() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<User> users = userDao.getAllUsers();
            mUsers.postValue(users);
        });
    }
}