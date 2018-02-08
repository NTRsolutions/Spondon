package com.example.user.login1.classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by User on 1/10/2018.
 */

public class LocationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long time = Calendar.getInstance().getTimeInMillis();
        Log.v("time", String.valueOf(time));
        //long interval = time + (4 * 60 * 60 * 1000);
        long interval = 60000;
        Log.v("interval", String.valueOf(interval));
        Intent aIntent = new Intent(context, LocationService.class);
        PendingIntent pIntent=PendingIntent.getService(context, 0, aIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pIntent);

        //Intent sintent = new Intent(context, LocationService.class);
        //context.startService(sintent);
        Log.v("broadCast", "Service called from broadcast");

    }

}
