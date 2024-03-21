package com.example.hermes_app.ui.comboNavDrawer.home;

import android.content.Intent;
import android.net.Uri;
import android.content.Intent;
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

import com.example.hermes_app.MainActivity;
import com.example.hermes_app.R;
import com.example.hermes_app.R;
import com.example.hermes_app.databinding.FragmentHomeBinding;
import com.example.hermes_app.ui.bottomNav.call.CallFragment;

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
        CardView informationCard = root.findViewById(R.id.cardView1);
        CardView callCard = root.findViewById(R.id.cardView2);
        CardView currentLocationCard = root.findViewById(R.id.cardView3);
        CardView caneLocationCard = root.findViewById(R.id.cardView4);
        CardView settingsCard = root.findViewById(R.id.cardView5);
        CardView emergencyCard = root.findViewById(R.id.cardView6);

        //CARD 1: information redirect
        informationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.cardInformationRedirect(requireActivity().getSupportFragmentManager());
            }
        });

        //CARD 2: call fragment redirect
        callCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.cardCallRedirect(requireActivity().getSupportFragmentManager());
            }
        });


        //CARD 3: current Location Redirect-> action done by viewmodel
        currentLocationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.buttonCurrentLocationRedirect(requireContext());
            }
        });

        //CARD 4: cane location redirect
        caneLocationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.cardCaneLocationRedirect(requireActivity().getSupportFragmentManager());
            }
        });

        //CARD 5: settings redirect
        settingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.cardSettingsRedirect(requireActivity().getSupportFragmentManager());
            }
        });

        //CARD 6: emergency redirect to phone (autodial)
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


        information_button = root.findViewById(R.id.information_button);
        information_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.buttonCurrentLocationRedirect(requireContext());
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