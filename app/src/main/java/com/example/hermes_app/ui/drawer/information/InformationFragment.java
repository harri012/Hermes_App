package com.example.hermes_app.ui.drawer.information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hermes_app.R;
import com.example.hermes_app.databinding.FragmentHomeBinding;

import org.w3c.dom.Text;

public class InformationFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView caneUID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InformationViewModel informationsViewModel =
                new ViewModelProvider(this).get(InformationViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        informationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        caneUID = root.findViewById(R.id.caneUIDLinked_textView);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}