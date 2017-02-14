package com.asworks.hrmobileapp_android;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_layout);
        SetCustomTitle(getString(R.string.main_title));

    }

    void SetCustomTitle(String title)
    {
        TextView textViewTitle = (TextView) findViewById(R.id.title_text);
        textViewTitle.setText(title);
    }
}
