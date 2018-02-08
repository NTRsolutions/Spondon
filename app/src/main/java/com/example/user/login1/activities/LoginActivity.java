package com.example.user.login1.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {
    EditText etUser, etPass;
    Button btnLogin;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//checking if logged in
        if (SharedPrefManager.getmInstance(this).isloggedin()) {
            startActivity(new Intent(getApplicationContext(), PatientActivity.class));
            finish();
            return;
        }

        etUser = (EditText) findViewById(R.id.etUsername);
        etPass = (EditText) findViewById(R.id.etPW);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        pd = new ProgressDialog(this);


    }

    //button for registration
    public void toregister(View view) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }

    //checking internet connection
    private boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    //button for login
    public void login(View view) {

        final String user = etUser.getText().toString().trim();
        final String pass = etPass.getText().toString().trim();

        if (!isOnline(LoginActivity.this)) {
            Toast.makeText(LoginActivity.this, "No network connection!", Toast.LENGTH_LONG).show();
            return;
        }
//JSOn parsing for login operation
        else {
            pd.setMessage("Please Wait...");
            pd.show();
            StringRequest sr = new StringRequest(Request.Method.POST, Constants.url_login,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            //Toast.makeText(getApplicationContext(),"Registration Successful.",Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jo = new JSONObject(response);
                                if (jo.getBoolean("error") != true) {
                                    SharedPrefManager.getmInstance(getApplicationContext()).userlogin(
                                            jo.getString("username")
                                    );
                                    Toast.makeText(getApplicationContext(), "Welcome Back!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), PatientActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_SHORT).show();
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
                    return params;
                }
            };

            RequestHandler.getmInstance(this).addToRequestQueue(sr);

        }
    }


}
