package com.asworks.hrmobileapp_android;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by mehmetugurgural on 12/02/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_layout);
        SetCustomTitle(getString(R.string.login_title));

    }

    void SetCustomTitle(String title)
    {
        TextView textViewTitle = (TextView) findViewById(R.id.title_text);
        textViewTitle.setText(title);
    }
}
