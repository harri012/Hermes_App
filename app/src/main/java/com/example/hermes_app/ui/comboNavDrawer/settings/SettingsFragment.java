package com.example.hermes_app.ui.comboNavDrawer.settings;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.widget.Spinner;

import com.example.hermes_app.R;
import com.example.hermes_app.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private Button saveButton;
    private Button removeButton;
    private EditText uid;
    private TextView uid_textView;
    private AudioManager audioManager;
    private SeekBar soundBar;

    private Button downVolume, upVolume;
    public String uid_saved;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);



        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        TextView text = (TextView) root.findViewById(R.id.spinnerTextView);
//        ImageView imageView =(ImageView)root.findViewById(R.id.spinnerImages);
//        Spinner spinner = (Spinner) root.findViewById(R.id.mySpinner);
//
//        SpinnerAdapter adapter = new SpinnerAdapter(root.getContext(), R.layout.spinner_value_layout, textArray, imageArray);
//        spinner.setAdapter(adapter);


        //locate Button Code -> action done by viewmodel
        saveButton = root.findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid_saved = uid.getText().toString();
                System.out.println(uid_saved);

                uid.setEnabled(false);
            }
        });


        removeButton = root.findViewById(R.id.remove_button);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid_saved = null;
                uid.setText(null);
                System.out.println("UID Removed: " + uid_saved);
            }
        });


        uid = root.findViewById(R.id.uid);
        String uid_text = uid.getText().toString();
        uid.setText(uid_text);

        audioManager = (AudioManager) this.getContext().getSystemService(root.getContext().AUDIO_SERVICE);
        soundBar = root.findViewById(R.id.soundSeekBar);


        upVolume = root.findViewById(R.id.volumeUpButton);
        upVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                soundBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        });

        //button behaviour when clicking on down
        downVolume = root.findViewById(R.id.volumeDownButton);
        downVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
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


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}