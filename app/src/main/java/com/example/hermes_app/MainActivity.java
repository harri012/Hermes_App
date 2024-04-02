package com.example.hermes_app;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hermes_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private FirebaseFirestore db;


    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        //fix dex issues
        File dexOutputDir = getCodeCacheDir();
        dexOutputDir.setReadOnly();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        bottomNavigationView.setBackground(null);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_settings, R.id.navigation_locate, R.id.navigation_call)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        //code the floating action button
        // Initialize Firebase Database
        db = FirebaseFirestore.getInstance();

        FloatingActionButton pingButton = findViewById(R.id.fab_ping);

        pingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePing();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    // Declare a boolean flag at the class level
    private boolean isPinging = false;
    // Declare a Handler at the class level
    private Handler pingHandler = new Handler();

    // Method to toggle the ping feature
    private void togglePing() {
        // Construct the document reference
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", this.MODE_PRIVATE);
        String uid = sharedPreferences.getString("uid", null);

        if (uid != null) {
            DocumentReference docRef = db.collection("devices")
                    .document(uid);

            // Update the boolean flag to its opposite value
            isPinging = !isPinging;

            // Update the boolean flag in Firebase
            docRef.update("ping", isPinging)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Boolean flag updated successfully!");

                            // Display a toast indicating the current state
                            if (isPinging) {
                                Toast.makeText(getApplicationContext(), "Pinging starting!", Toast.LENGTH_SHORT).show();
                                // Start a delayed runnable to cancel ping after 90 seconds
                                pingHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Cancel the ping operation after 90 seconds
                                        togglePing();
                                        // Alert the user that 90 seconds are up
                                        Toast.makeText(getApplicationContext(), "Ping stopped after 90 seconds!", Toast.LENGTH_SHORT).show();
                                    }
                                }, 90000); // 90 seconds delay (in milliseconds)
                            } else {
                                // Remove any pending callbacks when ping is canceled
                                pingHandler.removeCallbacksAndMessages(null);
                                Toast.makeText(getApplicationContext(), "Ping canceled!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error updating boolean flag: " + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Failed to update boolean flag: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Log.e(TAG, "UID is null, cannot construct document reference.");
            Toast.makeText(getApplicationContext(), "UID is null, unable to ping. Go to settings and enter cane UID.", Toast.LENGTH_SHORT).show();
        }
    }
}
