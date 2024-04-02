package com.example.hermes_app.ui.comboNavDrawer.settings;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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

    public LiveData<String> getText() {return uid;}


    private MutableLiveData<Boolean> locationEnabled = new MutableLiveData<>();

    public LiveData<Boolean> getLocationEnabled() {
        return locationEnabled;
    }

    public void setLocationEnabled(boolean enabled) {
        locationEnabled.setValue(enabled);
    }

    public void setLatestTimestampToTextView(TextView textView) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("devices")
                .document("004E00644652500520363830")
                .collection("location_data")
                .orderBy("timestamp", Query.Direction.DESCENDING) // Order documents by timestamp in descending order
                .limit(1) // Limit to the latest document
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Get the most recent document
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                        // Get the timestamp field from the document as a Timestamp object
                        Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");

                        if (timestamp != null) {
                            // Convert the timestamp to a string representation
                            String timestampString = timestamp.toDate().toString();

                            // Set the text value of the TextView with the latest timestamp
                            textView.setText("Last Seen: " + timestampString);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    Log.e(TAG, "Error getting latest timestamp: ", e);
                });
    }


}