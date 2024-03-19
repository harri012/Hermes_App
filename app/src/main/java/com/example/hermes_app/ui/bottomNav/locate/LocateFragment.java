package com.example.hermes_app.ui.bottomNav.locate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hermes_app.R;
import com.example.hermes_app.databinding.FragmentLocateBinding;

public class LocateFragment extends Fragment {

    private FragmentLocateBinding binding;
    Button locateButton;
    Button databaseLocationButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LocateViewModel locateViewModel =
                new ViewModelProvider(this).get(LocateViewModel.class);

        binding = FragmentLocateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLocate;
        locateViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //locate Button Code -> action done by viewmodel
        locateButton = root.findViewById(R.id.locate_button);

        locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locateViewModel.buttonRedirect(requireContext());
            }
        });

        //database locate button
        databaseLocationButton = root.findViewById(R.id.databaseLocation_button);

        databaseLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code
                locateViewModel.databaseLocateRedirect(requireContext());
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