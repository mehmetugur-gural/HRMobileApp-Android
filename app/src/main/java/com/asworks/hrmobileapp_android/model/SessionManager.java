package com.asworks.hrmobileapp_android.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

public class SessionManager {
    private SharedPreferences preferences;

    public SessionManager(Context cntx) {
        preferences = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void SetCurrentUser(Employee user) {
        SharedPreferences.Editor sharedPreferencesEditor = preferences.edit();
        final Gson gson = new Gson();
        sharedPreferencesEditor.putString("currentUser", gson.toJson(user));
        sharedPreferencesEditor.apply();
    }

    public Employee GetCurrentUser() {
        if (preferences.contains("currentUser")) {
            final Gson gson = new Gson();
            return gson.fromJson(preferences.getString("currentUser", ""), Employee.class);
        }
        return null;
    }
}