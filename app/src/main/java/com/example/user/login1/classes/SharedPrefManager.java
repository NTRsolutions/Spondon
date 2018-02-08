package com.example.user.login1.classes;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    final String prefName = "MySharedPref";
    final String keyUsername = "username";
    final String keyID = "userid";

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userlogin(String username) {
        SharedPreferences sp = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(keyUsername, username);
        editor.apply();
        return true;
    }

    public boolean isloggedin() {
        SharedPreferences sp = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        if (sp.getString(keyUsername, null) != null) {
            return true;
        } else
            return false;
    }

    public boolean logout() {
        SharedPreferences sp = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUser() {
        SharedPreferences sp = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sp.getString(keyUsername, null);
    }

}
