package com.example.hermes_app.ui.comboNavDrawer.locate;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

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

    private Context appContext;

    public LocateViewModel(Context context) {
        mText = new MutableLiveData<>();
        mText.setValue("This is locate fragment");
        locationData = new MutableLiveData<>();

        appContext = context.getApplicationContext();


        getLocationData();
    }

    public LiveData<String> getText() {
        return mText;
    }


    public LiveData<LocationData> getLocationData() {
        if (locationData.getValue() == null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            SharedPreferences sharedPreferences = appContext.getSharedPreferences("MyPrefs", appContext.MODE_PRIVATE);
            String uid = sharedPreferences.getString("uid", null);



            if (!uid.isEmpty())
            {
                db.collection("devices")
                        .document(uid)
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


            else
            {
                Toast.makeText(appContext, "UID does not exist.", Toast.LENGTH_SHORT).show();
            }



        }
        return locationData;
    }
}