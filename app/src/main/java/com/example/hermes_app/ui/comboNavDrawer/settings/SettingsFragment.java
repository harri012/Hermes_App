package com.example.hermes_app.ui.comboNavDrawer.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.widget.Spinner;

import com.example.hermes_app.R;
import com.example.hermes_app.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    String[] textArray = { "Normal", "Protanopia", "Tritanopia", "Deuteranopia" };
    Integer[] imageArray = { R.drawable.normal, R.drawable.protanopia,
            R.drawable.tritanopia, R.drawable.deuteranopia };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView text = (TextView) root.findViewById(R.id.spinnerTextView);
        ImageView imageView =(ImageView)root.findViewById(R.id.spinnerImages);
        Spinner spinner = (Spinner) root.findViewById(R.id.mySpinner);

        SpinnerAdapter adapter = new SpinnerAdapter(root.getContext(), R.layout.spinner_value_layout, textArray, imageArray);
        spinner.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}