package com.example.hermes_app.ui.comboNavDrawer.settings;


import static android.content.ContentValues.TAG;

import static com.example.hermes_app.MainActivity.sharedPreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hermes_app.R;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hermes_app.R;
import com.example.hermes_app.databinding.FragmentSettingsBinding;
import com.example.hermes_app.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel viewModel;
    private Button saveButton;
    private Button removeButton;
    private EditText uid;
    private TextView lastKnown;
    private AudioManager audioManager;
    private SeekBar soundBar;
    private Switch locateSwitch;


    public String uid_saved = null;
    private FirebaseAuth authentication;

    private Button downVolume, upVolume, logoutButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);


        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

/*        final TextView textView = binding.textSettings;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
//        TextView text = (TextView) root.findViewById(R.id.spinnerTextView);
//        ImageView imageView =(ImageView)root.findViewById(R.id.spinnerImages);
//        Spinner spinner = (Spinner) root.findViewById(R.id.mySpinner);
//
//        SpinnerAdapter adapter = new SpinnerAdapter(root.getContext(), R.layout.spinner_value_layout, textArray, imageArray);
//        spinner.setAdapter(adapter);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", root.getContext().MODE_PRIVATE);
        SharedPreferences sharedPreferencesUID = getActivity().getSharedPreferences("MyPrefs", root.getContext().MODE_PRIVATE);

        uid = root.findViewById(R.id.uid);
        String uid_text = uid.getText().toString();
        uid.setText(uid_text);

        String savedUid = sharedPreferencesUID.getString("uid", ""); // Retrieve the saved UID
        uid.setText(savedUid);

        //locate Button Code -> action done by viewmodel
        saveButton = root.findViewById(R.id.save_button);

   /*     saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid_saved = uid.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("uid", uid_saved);
                editor.apply();
                uid.setEnabled(false);
            }
        });
*/

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uidSaved = uid.getText().toString();

                // Check if the UID field is empty
                if (uidSaved.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a UID before saving!", Toast.LENGTH_SHORT).show();
                    return; // Exit the method without further processing
                }

                // Check if the document exists in Firestore
                DocumentReference docRef = FirebaseFirestore.getInstance().collection("devices").document(uidSaved);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Document exists in Firestore, proceed to save it in SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferencesUID.edit();
                                editor.putString("uid", uidSaved);
                                editor.apply();
                                uid.setEnabled(false);
                                Toast.makeText(getContext(), "UID saved successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Document doesn't exist in Firestore, clear the edit box and notify the user
                                uid.setText(""); // Clear the edit box
                                Toast.makeText(getContext(), "Cane does not exist. UID Field cleared. Please try again!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle errors
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });



        removeButton = root.findViewById(R.id.remove_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid.setText(""); // Clear the UID field
                SharedPreferences.Editor editor = sharedPreferencesUID.edit();
                editor.remove("uid"); // Remove the UID from SharedPreferences
                editor.apply();
                // Re-enable the UID field
                uid.setEnabled(true);
                Toast.makeText(getContext(), "Cane UID Removed!", Toast.LENGTH_SHORT).show();
            }
        });





        audioManager = (AudioManager) root.getContext().getSystemService(root.getContext().AUDIO_SERVICE);
        audioManager = (AudioManager) this.getContext().getSystemService(root.getContext().AUDIO_SERVICE);
        soundBar = root.findViewById(R.id.soundSeekBar);

        audioManager = (AudioManager) root.getContext().getSystemService(Context.AUDIO_SERVICE);
        soundBar = root.findViewById(R.id.soundSeekBar);

        upVolume = root.findViewById(R.id.volumeUpButton);
        upVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
                soundBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        });

        downVolume = root.findViewById(R.id.volumeDownButton);
        downVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
                soundBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        });



        //set the progress to match the device sound
        soundBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        soundBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        //seekbar adjustments
        soundBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //volume on progress
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //seekbar drag will be saved as new volume
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            //particular action
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //lift
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //SWITCH
        lastKnown = root.findViewById(R.id.lastKnownLocationText);
        //final TextView textView = binding.lastKnownLocationText;
        settingsViewModel.setLatestTimestampToTextView(lastKnown);

        locateSwitch = root.findViewById(R.id.locateSwitch);
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        // Restore the state of the switch from SharedPreferences
        SharedPreferences sharedPreferencesSwitch = requireContext().getSharedPreferences("SwitchState", Context.MODE_PRIVATE);
        boolean switchState = sharedPreferencesSwitch.getBoolean("locateSwitchState", true); // Default value true
        locateSwitch.setChecked(switchState);

        // Observe changes in the locationEnabled LiveData
        viewModel.getLocationEnabled().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean enabled) {
                // Update switch state
                locateSwitch.setChecked(enabled);
            }
        });

        // Set listener for switch changes
        locateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setLocationEnabled(isChecked);
                // Update Firebase database here
                updateFirebaseDatabase(isChecked);

                // Save switch state to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferencesSwitch.edit();
                editor.putBoolean("locateSwitchState", isChecked);
                editor.apply();
            }
        });


        //logout
        authentication = FirebaseAuth.getInstance();
        logoutButton = root.findViewById(R.id.logout_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //logout
    public void logout() {
        authentication.signOut();

        // Update "Remember Me" field to false
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Remember Me", false);
        editor.apply();

        // Start LoginActivity and finish the current activity
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


    private void updateFirebaseDatabase(boolean enabled) {
        // Update Firebase database here
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid != null) {
            String devicePath = "devices/004E00644652500520363830";
            DocumentReference docRef = FirebaseFirestore.getInstance().document(devicePath);
            docRef.update("gps", enabled)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Update successful
                            Toast.makeText(getContext(), "Cane Live Location " + (enabled ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle failure
                            Toast.makeText(getContext(), "Failed to update Cane Live Location Status", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}