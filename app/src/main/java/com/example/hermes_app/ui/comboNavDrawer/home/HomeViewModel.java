package com.example.hermes_app.ui.comboNavDrawer.home;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hermes_app.R;
import com.example.hermes_app.ui.bottomNav.call.CallFragment;
import com.example.hermes_app.ui.bottomNav.locate.LocateFragment;
import com.example.hermes_app.ui.bottomNav.locate.LocateViewModel;
import com.example.hermes_app.ui.comboNavDrawer.settings.SettingsFragment;
import com.example.hermes_app.ui.drawer.information.InformationFragment;

import java.util.Set;

import com.example.hermes_app.AboutUsActivity;
import com.example.hermes_app.InformationActivity;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    //CARD 1
    public void cardInformationRedirect(FragmentManager fragmentManager) {
        // Create an instance of the fragment you want to navigate to
        InformationFragment informationFragment = new InformationFragment();

        // Clear the fragment back stack
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // Begin a fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the new fragment
        fragmentTransaction.replace(R.id.fragment_container, informationFragment);

        // Commit the transaction
        fragmentTransaction.commit();
    }

    //CARD2
    public void cardCallRedirect(FragmentManager fragmentManager) {
        // Create an instance of the fragment you want to navigate to
        CallFragment callFragment = new CallFragment();


        // Begin a fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the new fragment
        fragmentTransaction.replace(R.id.fragment_container, callFragment);

        // Commit the transaction
        fragmentTransaction.commit();
    }

    //CARD3
    public void buttonCurrentLocationRedirect(Context context) {
        Intent intent = new Intent(context, LocateCurrent.class);
        context.startActivity(intent);
    }

    //CARD4
    public void cardCaneLocationRedirect(FragmentManager fragmentManager) {
        // Create an instance of the fragment you want to navigate to
        LocateFragment locateFragment = new LocateFragment();

        // Clear the fragment back stack
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // Begin a fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the new fragment
        fragmentTransaction.replace(R.id.fragment_container, locateFragment);

        // Commit the transaction
        fragmentTransaction.commit();
    }

    //CARD 5
    public void cardSettingsRedirect(FragmentManager fragmentManager) {
        // Create an instance of the fragment you want to navigate to
        SettingsFragment settingFragment = new SettingsFragment();

        // Clear the fragment back stack
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // Begin a fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the new fragment
        fragmentTransaction.replace(R.id.fragment_container, settingFragment);

        // Commit the transaction
        fragmentTransaction.commit();
    }

}