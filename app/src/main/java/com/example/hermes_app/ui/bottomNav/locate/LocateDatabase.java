package com.example.hermes_app.ui.bottomNav.locate;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.hermes_app.R;
import com.example.hermes_app.databinding.LocateDatabaseBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LocateDatabase extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocateDatabaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FirebaseApp.initializeApp(this);

        binding = LocateDatabaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //FirebaseApp.initializeApp(this);

        mMap = googleMap;

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
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            String latitudeString = snapshot.getString("latitude");
                            String longitudeString = snapshot.getString("longitude");


                            if (latitudeString != null && longitudeString != null) {
                                Log.d(TAG, "Latitude: " + latitudeString);
                                Log.d(TAG, "Longitude: " + longitudeString);

                                double latitude = Double.parseDouble(latitudeString);
                                double longitude = Double.parseDouble(longitudeString);

                                LatLng latLng = new LatLng(latitude, longitude);

                                // Add a marker and move the camera
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title("Marker of the Cane"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            } else {
                                Log.d(TAG, "Latitude or longitude is null");
                            }
                        } else {
                            Log.d(TAG, "Current data: null");
                        }
                    }
                });
    }

}

