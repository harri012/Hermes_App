package com.example.hermes_app.ui.comboNavDrawer.home;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hermes_app.R;
import com.example.hermes_app.ui.bottomNav.call.CallFragment;
import com.example.hermes_app.ui.bottomNav.locate.LocateFragment;
import com.example.hermes_app.ui.bottomNav.locate.LocateViewModel;
import com.example.hermes_app.ui.comboNavDrawer.settings.SettingsFragment;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void cardCallRedirect(FragmentManager fragmentManager) {
        // Create an instance of the fragment you want to navigate to
        CallFragment callFragment = new CallFragment();

        // Clear the fragment back stack
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // Begin a fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the new fragment
        fragmentTransaction.replace(R.id.fragment_container, callFragment);

        // Commit the transaction
        fragmentTransaction.commit();
    }

    public void buttonCurrentLocationRedirect(Context context) {
        Intent intent = new Intent(context, LocateCurrent.class);
        context.startActivity(intent);
    }

    public void cardCaneLocationRedirect(Context context) {
        Intent intent = new Intent(context, LocateFragment.class);
        context.startActivity(intent);
    }

    public void cardSettingsRedirect(Context context) {
        Intent intent = new Intent(context, SettingsFragment.class);
        context.startActivity(intent);
    }
}