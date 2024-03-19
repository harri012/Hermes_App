package com.example.hermes_app.ui.locate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocateViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LocateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is locate fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}