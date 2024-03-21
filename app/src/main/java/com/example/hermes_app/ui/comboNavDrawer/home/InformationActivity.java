package com.example.hermes_app.ui.comboNavDrawer.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;


import com.example.hermes_app.R;

public class InformationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        // Find the toolbar view and set it as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set title
        getSupportActionBar().setTitle("User Guidelines");
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle the back button click
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
}
