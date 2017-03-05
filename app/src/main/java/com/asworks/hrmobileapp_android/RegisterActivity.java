package com.asworks.hrmobileapp_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Yeni Kayıt");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton btnRegister = (ImageButton)findViewById(R.id.btnCompleteRegister);
        btnRegister.setOnClickListener(btnRegisterClick);

    }

    private View.OnClickListener btnRegisterClick = new View.OnClickListener() {
        public void onClick(View v) {

        Toast.makeText(getApplicationContext(), "Üyeliğiniz Yöneticilerimiz Tarafından Onaylandıktan Sonra Aktif Olacaktır.", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}