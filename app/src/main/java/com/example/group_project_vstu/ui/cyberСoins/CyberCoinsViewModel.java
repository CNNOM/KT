package com.example.group_project_vstu.ui.cyberСoins;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CyberCoinsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CyberCoinsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is CyberCoins fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}