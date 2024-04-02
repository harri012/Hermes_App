package com.example.hermes_app.ui.comboNavDrawer.settings;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import java.util.Arrays;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<String> uid = new MutableLiveData<>();
    private final MutableLiveData<String> lastKnownText;
    private MutableLiveData<List<String>> colorPaletteLiveData = new MutableLiveData<>();
    private MediatorLiveData<String> combinedLiveData = new MediatorLiveData<>();


    public SettingsViewModel() {
        lastKnownText = new MutableLiveData<>();
        lastKnownText.setValue("Last Seen: ");
    }

    public LiveData<String> getLastKnownText() {
        return lastKnownText;
    }

    public LiveData<String> getText() {
        return uid;
    }
}