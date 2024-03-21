package com.example.hermes_app.ui.comboNavDrawer.call;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hermes_app.R;
import com.example.hermes_app.databinding.FragmentCallBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CallFragment extends Fragment {

    private FragmentCallBinding binding;

    private EditText phoneNumber;
    private FloatingActionButton callButton;
    private FloatingActionButton sosButton;
    static int PERMISSION_CODE=100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CallViewModel callViewModel =
                new ViewModelProvider(this).get(CallViewModel.class);

        binding = FragmentCallBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCall;
        callViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //call function direct
        phoneNumber = root.findViewById(R.id.editTextPhone_number);
        callButton = root.findViewById(R.id.floatingActionButton_call);
        sosButton = root.findViewById(R.id.floatingActionButton_sos);


        //check if call is granted
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
        }

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneNumber.getText().toString();
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + phone));
                startActivity(i);
            }
        });

        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);

                //call emergency services
                callIntent.setData(Uri.parse("tel:911"));

                //redirect
                startActivity(callIntent);
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