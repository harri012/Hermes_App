package com.example.hermes_app.ui.bottomNav.locate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hermes_app.databinding.FragmentLocateBinding;

public class LocateFragment extends Fragment {

    private FragmentLocateBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LocateViewModel locateViewModel =
                new ViewModelProvider(this).get(LocateViewModel.class);

        binding = FragmentLocateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLocate;
        locateViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}