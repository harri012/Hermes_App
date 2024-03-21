package com.example.hermes_app.ui.comboNavDrawer.home;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome to");
    }

    public LiveData<String> getText() {
        return mText;
    }


    //CARD1
    public void buttonCurrentLocationRedirect(Context context) {
        Intent intent = new Intent(context, LocateCurrent.class);
        context.startActivity(intent);
    }

    //Card3
    public void buttonAboutRedirect(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

    //card 4
    public void buttonInfoRedirect(Context context) {
        Intent intent = new Intent(context, InformationActivity.class);
        context.startActivity(intent);
    }

}