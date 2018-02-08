package com.example.user.login1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.user.login1.R;

public class BloodRequestInfo extends AppCompatActivity {
    EditText etPname,etPhone,etPLocation,etDate,etTime,etCondition;
    String bg,latitude,longitude;
    String Pname,Phone,PLocation,Date,Time,Condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request_info);
        etCondition=findViewById(R.id.etCondition);
        etDate=findViewById(R.id.etDate);
        etPname=findViewById(R.id.etPName);
        etPhone=findViewById(R.id.etPhone);
        etPLocation=findViewById(R.id.etLocation);
        etTime=findViewById(R.id.etTime);


        latitude = getIntent().getExtras().getString("latitude");
        longitude = getIntent().getExtras().getString("longitude");
        bg = getIntent().getExtras().getString("bg");
        Log.d("check bg from BRInfo", bg);
        Log.d("check from BRInfo", Pname+" "+Phone+" "+bg);


    }

    public void searchBlood(View view){
        Pname=etPname.getText().toString().trim();
        Phone=etPhone.getText().toString().trim();
        PLocation=etPLocation.getText().toString().trim();
        Date=etDate.getText().toString().trim();
        Time=etTime.getText().toString().trim();
        Condition=etCondition.getText().toString().trim();

        Intent i=new Intent(getApplicationContext(),SearchActivitySample.class);
        i.putExtra("pname",Pname);
        i.putExtra("phone",Phone);
        i.putExtra("plocation",PLocation);
        i.putExtra("date",Date);
        i.putExtra("time",Time);
        i.putExtra("condition",Condition);

        i.putExtra("bg", bg);
        i.putExtra("latitude",latitude);
        i.putExtra("longitude", longitude);
        startActivity(i);
        Log.d("check from BRInfo", Pname+" "+Phone+" "+bg);


    }
}
