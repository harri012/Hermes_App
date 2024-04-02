package com.example.hermes_app.ui.comboNavDrawer.call;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    private ActivityResultLauncher<String> requestPermissionLauncher;


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


        // Check if call permission is granted
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Initialize the ActivityResultLauncher
            requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    // Permission denied, inform the user
                    Toast.makeText(requireContext(), "Call permission denied. You cannot make calls.", Toast.LENGTH_SHORT).show();
                }
            });

            // Request call permission
            requestCallPermission();
        }

        // Set click listener for the call button
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if call permission is granted
                if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, proceed with making the call
                    String phone = phoneNumber.getText().toString();
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + phone));
                    startActivity(i);
                } else {
                    // Permission not granted, show the permission dialog
                    showPermissionDialog();
                }
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

    private void requestCallPermission() {
        // Launch the permission request
        requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE);
    }

    private void showPermissionDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Call Permission Required")
                .setMessage("Call permission is required to make calls. Do you want to grant the permission now? If grant does not work, go to app settings")
                .setPositiveButton("Grant", (dialog, which) -> {
                    // Request call permission again
                    requestCallPermission();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // User canceled the permission request
                    Toast.makeText(requireContext(), "Call permission denied. You cannot make calls.", Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton("Go to Settings", (dialog, which) -> {
                    // Open app settings where the user can manually enable the permission
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .show();
    }

}