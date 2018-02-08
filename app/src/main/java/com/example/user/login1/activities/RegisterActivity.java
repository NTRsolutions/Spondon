package com.example.user.login1.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.login1.R;
import com.example.user.login1.classes.Constants;
import com.example.user.login1.classes.RequestHandler;
import com.example.user.login1.classes.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText etUser, etPass, etEmail,etBG;
    Button btnRegister;
    ProgressDialog pd;
    String latitude=null,longitude=null;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (SharedPrefManager.getmInstance(this).isloggedin()) {
            startActivity(new Intent(getApplicationContext(), PatientActivity.class));
            finish();
            return;
        }

        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        etEmail = (EditText) findViewById(R.id.etPass);
        etBG=findViewById(R.id.etbg);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        pd = new ProgressDialog(this);
        //pd.setMessage();


    }

    private boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void register(View view) {
        final String user = etUser.getText().toString().trim();
        final String pass = etPass.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String bg = etBG.getText().toString().trim();


        if (!isOnline(RegisterActivity.this)) {
            Toast.makeText(RegisterActivity.this, "No network connection!", Toast.LENGTH_LONG).show();
            return;
        } else {
            show();
            pd.setMessage("Registering User...");
            pd.show();
            StringRequest sr = new StringRequest(Request.Method.POST, Constants.url_register,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            //Toast.makeText(getApplicationContext(),"Registration Successful.",Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jo = new JSONObject(response);
                                Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_SHORT).show();
                                if (!jo.getBoolean("error")) {
                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(i);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.hide();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", user);
                    params.put("password", pass);
                    params.put("email", email);
                    params.put("bg", bg);
                    params.put("latitude", latitude);
                    params.put("longitude", longitude);


                    return params;
                }
            };
            RequestHandler.getmInstance(this).addToRequestQueue(sr);
        }

    }

    public void show() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            getLocation();
        } else if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            getLocation();

        }
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {


            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                Log.d("check method", "network");

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                Log.d("check method", "passive");

            }
        }

    }


}
