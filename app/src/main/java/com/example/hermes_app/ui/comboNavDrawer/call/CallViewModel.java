package com.example.hermes_app.ui.comboNavDrawer.call;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CallViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CallViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Dial a Number or Click on the Emergency Services Button");
    }

    public LiveData<String> getText() {
        return mText;
    }
}