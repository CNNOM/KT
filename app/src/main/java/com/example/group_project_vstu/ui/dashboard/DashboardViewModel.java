package com.example.group_project_vstu.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.group_project_vstu.AppDatabase;
import com.example.group_project_vstu.User;
import com.example.group_project_vstu.UserDao;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<User>> mStudents;
    private final UserDao userDao;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mStudents = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");

        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();

        loadStudents();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<User>> getStudents() {
        return mStudents;
    }

    private void loadStudents() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<User> students = userDao.getStudents();
            mStudents.postValue(students);
        });
    }
}