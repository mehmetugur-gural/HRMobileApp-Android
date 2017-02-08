package com.asworks.hrmobileapp_android;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_layout);
        SetCustomTitle(getString(R.string.login_title));

        ImageButton btnLogin = (ImageButton)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(BtnLoginClick);
    }

    private View.OnClickListener BtnLoginClick = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
        }
    };

    void SetCustomTitle(String title)
    {
        TextView textViewTitle = (TextView) findViewById(R.id.title_text);
        textViewTitle.setText(title);
    }
}
