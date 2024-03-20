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
    private MutableLiveData<String> textLiveData = new MutableLiveData<>();
    private MutableLiveData<List<String>> colorPaletteLiveData = new MutableLiveData<>();
    private MediatorLiveData<String> combinedLiveData = new MediatorLiveData<>();


    public SettingsViewModel() {


        textLiveData.setValue("Color Palettes");
        List<String> colorPaletteNames = Arrays.asList("normal", "protanopia", "deuteranopia", "tritanopia");
        colorPaletteLiveData.setValue(colorPaletteNames);



    }

    public LiveData<String> getText() {
        return textLiveData;
    }

    public LiveData<List<String>> getColorPaletteLiveData() {
        return colorPaletteLiveData;
    }

}