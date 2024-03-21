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
                updateFlag(true);
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

    // Method to update boolean flag in Firebase Database
    private void updateFlag(boolean value) {
        // Construct the document reference

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", this.MODE_PRIVATE);
        String uid = sharedPreferences.getString("uid", null);

        if (uid != null) {
            DocumentReference docRef = db.collection("devices")
                    .document(uid);

            // Update the boolean flag
            docRef.update("ping", value)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            boolean flag = true; // Set the flag to true
                            Log.d(TAG, "Boolean flag updated successfully!");

                            // Display a toast indicating success
                            Toast.makeText(getApplicationContext(), "Pinging!", Toast.LENGTH_SHORT).show();

                            // Revert the flag back to false after 30 seconds
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateFlag(false);
                                }
                            }, 90000); // 30 seconds delay (in milliseconds)
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
            Toast.makeText(getApplicationContext(), "UID is null, unable to ping", Toast.LENGTH_SHORT).show();
        }
    }
}

