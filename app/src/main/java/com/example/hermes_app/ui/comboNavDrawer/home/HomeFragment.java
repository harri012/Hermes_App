package com.example.hermes_app.ui.comboNavDrawer.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hermes_app.R;
import com.example.hermes_app.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button information_button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //CARDS init
        CardView currentLocationCard = root.findViewById(R.id.cardView1);
        CardView emergencyCard = root.findViewById(R.id.cardView2);
        CardView aboutCard = root.findViewById(R.id.cardView3);
        CardView informationCard = root.findViewById(R.id.cardView4);


        //CARD 1: current Location Redirect-> action done by viewmodel
        currentLocationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.buttonCurrentLocationRedirect(requireContext());
            }
        });

        //CARD 2: call emergency redirect
        emergencyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);

                //call emergency services
                callIntent.setData(Uri.parse("tel:911"));

                //redirect
                startActivity(callIntent);
            }
        });


        //CARD 3: aboutUs
        aboutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.buttonAboutRedirect(requireContext());
            }
        });

        //CARD 4: Info
        informationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.buttonInfoRedirect(requireContext());
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}