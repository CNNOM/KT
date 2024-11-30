package com.example.group_project_vstu.ui.notifications;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> mFileContent;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mFileContent = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getFileContent() {
        return mFileContent;
    }

    public void loadFileContent(Context context, int resourceId) {
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            mFileContent.setValue(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            mFileContent.setValue("Error loading file: " + e.getMessage());
        }
    }
}