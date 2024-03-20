package com.example.hermes_app.ui.bottomNav.locate;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hermes_app.ui.bottomNav.locate.LocateCurrent;
import com.example.hermes_app.ui.bottomNav.locate.LocationData;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LocateViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<LocationData> locationData;
    private GoogleMap mMap;
    private FirebaseFirestore db;

    public LocateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is locate fragment");
        locationData = new MutableLiveData<>();
        getLocationData();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void buttonRedirect(Context context) {
        Intent intent = new Intent(context, LocateCurrent.class);
        context.startActivity(intent);
    }

    public LiveData<LocationData> getLocationData() {
        if (locationData.getValue() == null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("devices")
                    .document("003600124150500920393333")
                    .collection("location_data")
                    .document("0STj1KpG20L7rFspKXbi")
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot snapshot,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                // Handle error
                                return;
                            }

                            if (snapshot != null && snapshot.exists()) {
                                String latitudeString = snapshot.getString("latitude");
                                String longitudeString = snapshot.getString("longitude");

                                if (latitudeString != null && longitudeString != null) {
                                    double latitude = Double.parseDouble(latitudeString);
                                    double longitude = Double.parseDouble(longitudeString);

                                    LocationData data = new LocationData(latitude, longitude);
                                    locationData.postValue(data); // Post value to the main thread
                                }
                            }
                        }
                    });
        }
        return locationData;
    }
}
