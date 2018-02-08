package com.example.user.login1.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login1.R;
import com.example.user.login1.classes.LocationReceiver;
import com.example.user.login1.classes.LocationService;
import com.example.user.login1.classes.SharedPrefManager;

public class PatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tv;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    CardView sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        //tv = (TextView) findViewById(R.id.tvUsername);
        sb = findViewById(R.id.search_blood);
        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigation_viw);
        navigationView.setNavigationItemSelectedListener(this);

        if (!SharedPrefManager.getmInstance(this).isloggedin()) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }

        Intent aIntent = new Intent(getApplicationContext(), LocationReceiver.class);
        sendBroadcast(aIntent);

        //tv.setText(SharedPrefManager.getmInstance(this).getUser());
        //alarmService();
    }

    void alarmService() {
        /*long time = Calendar.getInstance().getTimeInMillis();
        Log.v("time", String.valueOf(time));
        //long interval = time + (4 * 60 * 60 * 1000);
        long interval = 10000;
        Log.v("interval", String.valueOf(interval));
        Intent aIntent = new Intent(getApplicationContext(), LocationReceiver.class);
        PendingIntent pIntent=PendingIntent.getBroadcast(getApplicationContext(), 0, aIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, time, interval, pIntent);*/


    }

    /* For Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        /* For Actionbar menu
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPrefManager.getmInstance(this).logout();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
        }
        return true;*/
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {
            Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.update) {
            Toast.makeText(this, "Update Selected", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.availability) {
            Toast.makeText(this, "Availability Selected", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.rate) {
            Toast.makeText(this, "Rate Selected", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.logout) {
            Toast.makeText(this, "Logout Selected", Toast.LENGTH_SHORT).show();
            SharedPrefManager.getmInstance(this).logout();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();

            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            stopService(intent);
        }

        return false;
    }

    public void patientActivity(View view) {
        int id = view.getId();
        if (id == R.id.search_blood) {
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        }
        if (id == R.id.sent_request) {
            startActivity(new Intent(getApplicationContext(), GetLocation.class));
        }

    }


}
