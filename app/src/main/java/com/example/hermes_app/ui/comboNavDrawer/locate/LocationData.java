package com.example.hermes_app.ui.comboNavDrawer.locate;

public class LocationData {
    private double latitude;
    private double longitude;

    public LocationData(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
