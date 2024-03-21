package com.example.hermes_app.ui.comboNavDrawer.locate;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.hermes_app.R;
import com.example.hermes_app.databinding.FragmentLocateBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class LocateFragment extends Fragment implements OnMapReadyCallback {

    private FragmentLocateBinding binding;
    private AppBarConfiguration appBarConfiguration;


    private GoogleMap mMap;
    private LocateViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LocateViewModel locateViewModel =
                new ViewModelProvider(this).get(LocateViewModel.class);

        binding = FragmentLocateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        viewModel = new ViewModelProvider(this).get(LocateViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
        mMap = googleMap;

        // Padding moves the components
        int paddingLeft, paddingTop, paddingRight, paddingBottom ;
        paddingLeft = 0;
        paddingTop=0;
        paddingRight=0;
        paddingBottom=200;
        mMap.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

        viewModel.getLocationData().observe(getViewLifecycleOwner(), locationData -> {
            if (locationData != null) {
                double latitude = locationData.getLatitude();
                double longitude = locationData.getLongitude();

                LatLng latLng = new LatLng(latitude, longitude);

                // Add a marker and move the camera
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Marker of the Cane"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            } else {
                Log.d(TAG, "Location data is null");
            }
        });
    }
}





