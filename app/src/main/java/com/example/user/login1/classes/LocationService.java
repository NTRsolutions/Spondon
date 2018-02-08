package com.example.user.login1.classes;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by User on 1/8/2018.
 */

public class LocationService extends Service {
    String latitude = null, longitude = null;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String username;
    int provider;
    final String url_update_location="http://amatik2015.000webhostapp.com/login/updatelocation.php";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(), "Service Started!", Toast.LENGTH_SHORT).show();
        show();

        Log.d("CHECK Service", "Service Started" + " for user:" + username);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "Service Stopped!", Toast.LENGTH_SHORT).show();
        Log.d("CHECK Service", "Service Stopped");
        super.onDestroy();
    }

    public void show() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            provider=1;
            getLocation();
        } else if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            provider=2;
            getLocation();
        }
    }

    private void getLocation() {

        Log.d("check service provider", "from service getLocation");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        if (provider == 1) {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                Log.d("check service provider", "network");
                Log.d("check location", latitude + " " + longitude);

            }
        }
        if (provider == 2) {
            Location location1 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                Log.d("check service provider:", "passive");
                Log.d("check location", latitude + " " + longitude);

            }
        }

        register();
        stopSelf();
    }


    public void register() {
        try {
            String user = SharedPrefManager.getmInstance(this).getUser();
            username = user;
        } catch (Exception e) {

        }

        StringRequest sr = new StringRequest(Request.Method.POST,url_update_location,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_SHORT).show();
                            Log.d("CHECK Service", "location updated");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("CHECK Service", "json error in service");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("latitude", latitude);
                params.put("longitude", longitude);

                Log.d("CHECK Service", username);
                return params;
            }
        };
        RequestHandler.getmInstance(this).addToRequestQueue(sr);

    }

}

