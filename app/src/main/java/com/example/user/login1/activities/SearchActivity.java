package com.example.user.login1.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.user.login1.R;

public class SearchActivity extends AppCompatActivity {
    CardView aP, aN, bP, bN, oP, oN, abP, abN;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude, longitude;
    boolean gps_check;
    int provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        aP = findViewById(R.id.aPlus);
        aN = findViewById(R.id.aMinus);
        abP = findViewById(R.id.abPlus);
        abN = findViewById(R.id.abMinus);
        bP = findViewById(R.id.bPlus);
        bN = findViewById(R.id.bMinus);
        oP = findViewById(R.id.oPlus);
        oN = findViewById(R.id.oMinus);

        //buildAlertMessageNoGps();
        show();
    }

    public void show() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            getLocation();
            provider = 1;
            Log.d("check method", "network condition");
        } else if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            getLocation();
            provider = 2;
            Log.d("check method", "passive condition");

        } else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            Log.d("check method", "gps alert");

        } else if (gps_check && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
            provider = 3;
            Log.d("check method", "gps condition");
        }
        Log.d("check provider", String.valueOf(provider));
    }


    private void getLocation() {
        Log.d("check method", "from getLocation");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            buildAlertMessageNoGps();
            Log.d("check method", "requesting permission");

        } else {

            if (provider == 1) {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    double latti = location.getLatitude();
                    double longi = location.getLongitude();
                    lattitude = String.valueOf(latti);
                    longitude = String.valueOf(longi);
                    Log.d("check method", "provider:"+provider);

                } else {

                    Toast.makeText(this, "Unble to Trace your location, Please On your Location/GPS!", Toast.LENGTH_LONG).show();


                }
                Log.d("check method", "network");
            }
            if (provider == 2) {
                Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if (location2 != null) {
                    double latti = location2.getLatitude();
                    double longi = location2.getLongitude();
                    lattitude = String.valueOf(latti);
                    longitude = String.valueOf(longi);
                    Log.d("check method", "provider:"+provider);

                } else {

                    Toast.makeText(this, "Unble to Trace your location, Please On your Location/GPS!", Toast.LENGTH_LONG).show();

                }
                Log.d("check method", "passive");

            }
            if (provider == 3) {
                Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location1 != null) {
                    double latti = location1.getLatitude();
                    double longi = location1.getLongitude();
                    lattitude = String.valueOf(latti);
                    longitude = String.valueOf(longi);
                    Log.d("check method", "provider:"+provider);

                } else {

                    Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show();

                }
                Log.d("check method", "gps");

            }
        }

    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        gps_check = true;

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
        Log.d("check method", "from alert");
    }


    public void bloodSearch(View view) {

        show();

        int id = view.getId();
        if (id == R.id.aPlus) {
            Intent i = new Intent(getApplicationContext(), BloodRequestInfo.class);
            i.putExtra("bg", "a+");
            i.putExtra("latitude", lattitude);
            i.putExtra("longitude", longitude);
            startActivity(i);

        }
        if (id == R.id.aMinus) {
            Intent i = new Intent(getApplicationContext(), BloodRequestInfo.class);
            i.putExtra("bg", "a-");
            i.putExtra("latitude", lattitude);
            i.putExtra("longitude", longitude);
            startActivity(i);
        }
        if (id == R.id.bPlus) {
            Intent i = new Intent(getApplicationContext(), BloodRequestInfo.class);
            i.putExtra("bg", "b+");
            i.putExtra("latitude", lattitude);
            i.putExtra("longitude", longitude);
            startActivity(i);
        }
        if (id == R.id.bMinus) {
            Intent i = new Intent(getApplicationContext(), BloodRequestInfo.class);
            i.putExtra("bg", "b-");
            i.putExtra("latitude", lattitude);
            i.putExtra("longitude", longitude);
            startActivity(i);
        }
        if (id == R.id.abPlus) {
            Intent i = new Intent(getApplicationContext(), BloodRequestInfo.class);
            i.putExtra("bg", "ab+");
            i.putExtra("latitude", lattitude);
            i.putExtra("longitude", longitude);
            startActivity(i);
        }
        if (id == R.id.abMinus) {
            Intent i = new Intent(getApplicationContext(), BloodRequestInfo.class);
            i.putExtra("bg", "ab-");
            i.putExtra("latitude", lattitude);
            i.putExtra("longitude", longitude);
            startActivity(i);
        }
        if (id == R.id.oPlus) {
            Intent i = new Intent(getApplicationContext(), SearchActivitySample.class);
            i.putExtra("bg", "o+");
            i.putExtra("latitude", lattitude);
            i.putExtra("longitude", longitude);
            startActivity(i);
        }
        if (id == R.id.oMinus) {
            Intent i = new Intent(getApplicationContext(), BloodRequestInfo.class);
            i.putExtra("bg", "o-");
            i.putExtra("latitude", lattitude);
            i.putExtra("longitude", longitude);
            startActivity(i);
        }
    }
}
