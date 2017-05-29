package com.asworks.hrmobileapp_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mehmetugurgural on 20/05/2017.
 */

public class SettingsCVActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_cv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CV & Dosyalarım");
    }
}
