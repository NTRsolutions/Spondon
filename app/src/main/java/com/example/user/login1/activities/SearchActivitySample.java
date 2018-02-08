package com.example.user.login1.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.login1.R;
import com.example.user.login1.classes.Constants;
import com.example.user.login1.classes.DataDonor;
import com.example.user.login1.classes.DonorCustomAdapter;
import com.example.user.login1.classes.RequestHandler;
import com.example.user.login1.classes.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivitySample extends AppCompatActivity {
    ProgressDialog pd;
    int status;
    RecyclerView recyclerView;
    List<DataDonor> donorList;
    DonorCustomAdapter adapter;
    static String Pname, PPhone, PLocation, Date, Time, Condition;
    String DUser, Dname, DLocation, DPhone, Distance;
    static String PUser;
    static String bg, latitude, longitude;
    TextView emptyDonors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sample);
        emptyDonors = findViewById(R.id.emptyDonors);
        pd = new ProgressDialog(this);
        donorList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        latitude = getIntent().getExtras().getString("latitude");
        longitude = getIntent().getExtras().getString("longitude");
        bg = getIntent().getExtras().getString("bg");

        PUser = SharedPrefManager.getmInstance(this).getUser();
        Pname = getIntent().getExtras().getString("pname");
        PLocation = getIntent().getExtras().getString("plocation");
        PPhone = getIntent().getExtras().getString("phone");
        Date = getIntent().getExtras().getString("date");
        Time = getIntent().getExtras().getString("time");
        Condition = getIntent().getExtras().getString("condition");
        Log.d("check bg from SAS", bg);
        Log.d("check from SAS", PUser + " " + Pname);
//        Log.d("check latitude", latitude);
        //       Log.d("check longitude", longitude);*/

        search();


    }




    private boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void search() {

        //final String bg = etBG.getText().toString().trim();
        //final String latitude = "34";
        //final String longitude = "-115";
        pd.setMessage("Please Wait...");
        pd.show();

        //checking internet connection
        if (!isOnline(this)) {
            Toast.makeText(this, "No network connection!", Toast.LENGTH_LONG).show();
            pd.dismiss();
            return;
        } else {
//JSon parsing with string request
            StringRequest sr = new StringRequest(Request.Method.POST, Constants.url_search,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj = array.getJSONObject(i);

                                    String name = obj.getString("name");
                                    String location = obj.getString("location");
                                    String clocation = obj.getString("clocation");
                                    int idistance = obj.getInt("distance");
                                    String distance = String.valueOf(idistance);

                                    Log.d("check", obj.getString("distance"));

                                    String user = obj.getString("username");
                                    String phone = obj.getString("phone");

                                    DataDonor data = new DataDonor(user, name, phone, location, clocation, distance);
                                    donorList.add(data);


                                }

                                //checking empty recyclerview
                                if (!donorList.isEmpty()) {
                                    emptyDonors.setVisibility(View.GONE);
                                    adapter = new DonorCustomAdapter(getApplicationContext(), donorList);
                                    recyclerView.setAdapter(adapter);
                                } else {
                                    recyclerView.setVisibility(View.INVISIBLE);
                                    emptyDonors.setVisibility(View.VISIBLE);

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                //sending value for db operation
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("latitude", latitude);
                    params.put("longitude", longitude);
                    params.put("bg", bg);
                    params.put("username", PUser);
                    //Log.d("check bg value",bg);
                    return params;
                }
            };

            RequestHandler.getmInstance(this).addToRequestQueue(sr);

        }

    }

    public int sendRequest(final String duser, final String dname, final String dphone, final String dlocation, final String distance) {
        Log.d("CHECK from Method", duser);
        Log.d("CHECK from Method", PUser + " " + Pname);

        //pd.setMessage("Requesting...");
        //pd.show();
//JSon parsing with string request
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.url_send_request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONObject jo = new JSONObject(response);
                            if (jo.getBoolean("error") != true) {
                                status = 1;
                                //Toast.makeText(getApplicationContext(), "Request Sent!", Toast.LENGTH_SHORT).show();
                                Log.d("CHECK Request", "Request sent!");

                            } else {
                                //Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_SHORT).show();
                                Log.d("CHECK Request", "error sending request");
                                status=0;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        status = 0;
                        Log.d("CHECK request response:", "json error");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("bg", bg);
                params.put("puser", PUser);
                params.put("pname", Pname);
                params.put("plocation", PLocation);
                params.put("pphone", PPhone);
                params.put("duser", duser);
                params.put("dname", dname);
                params.put("dlocation", dlocation);
                params.put("dphone", dphone);
                params.put("distance", distance);
                params.put("date", Date);
                params.put("time", Time);
                params.put("condition", Condition);

                Log.d("CHECK P values", bg + " user:" + PUser + " name:" + Pname + " loc:" + PLocation + " phone:" + PPhone + " date:" + Date + " time:" + Time + " con:" + Condition);
                Log.d("CHECK D values", duser + " name:" + dname + " loc:" + dlocation + " phone:" + dphone + " dist:" + distance);

                return params;
            }
        };
        RequestHandler.getmInstance(this).addToRequestQueue(sr);

        Log.d("CHECK status:", String.valueOf(status));
        return status;
    }

}
