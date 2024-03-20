package com.example.hermes_app.ui.bottomNav.locate;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.FirebaseApp;

public class LocateViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LocateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is locate fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void buttonRedirect(Context context){
        Intent intent = new Intent(context, LocateCurrent.class);
        context.startActivity(intent);
    }

    public void databaseLocateRedirect(Context context){
        FirebaseApp.initializeApp(context);

        Intent intent = new Intent(context, LocateDatabase.class);
        context.startActivity(intent);
    }
}