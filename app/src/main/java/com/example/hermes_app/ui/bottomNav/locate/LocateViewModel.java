package com.example.hermes_app.ui.bottomNav.locate;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class LocateViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<LocationData> locationData;

    public LocateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is locate fragment");
        locationData = new MutableLiveData<>();
        getLocationData();
    }

    public LiveData<String> getText() {
        return mText;
    }


    public LiveData<LocationData> getLocationData() {
        if (locationData.getValue() == null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("devices")
                    .document("004E00644652500520363830")
                    .collection("location_data")
                    .orderBy("timestamp", Query.Direction.DESCENDING) // Order documents by timestamp in descending order
                    .limit(1) // Limit to the latest document
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshot,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                // Handle error
                                return;
                            }

                            if (snapshot != null && !snapshot.isEmpty()) {
                                // Get the latest document from the collection
                                DocumentSnapshot latestSnapshot = snapshot.getDocuments().get(0);

                                String latitudeString = latestSnapshot.getString("latitude");
                                String longitudeString = latestSnapshot.getString("longitude");

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