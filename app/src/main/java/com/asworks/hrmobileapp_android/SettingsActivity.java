package com.asworks.hrmobileapp_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Profilim");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnProfileInfo = (Button)findViewById(R.id.btnProfileInfo);
        btnProfileInfo.setOnClickListener(BtnProfileInfoClick);

        Button btnSizeInfo = (Button)findViewById(R.id.btnSizeInfo);
        btnSizeInfo.setOnClickListener(BtnSizeInfoClick);

        Button btnAvailableInfo = (Button)findViewById(R.id.btnAvailableInfo);
        btnAvailableInfo.setOnClickListener(BtnAvailableInfoClick);

        Button btnProfilePictures = (Button)findViewById(R.id.btnProfilePictures);
        btnProfilePictures.setOnClickListener(BtnProfilePicturesClick);

//        Button btnProfileCV = (Button)findViewById(R.id.btnProfileCV);
//        btnProfileCV.setOnClickListener(BtnProfileCVClick);

        Button btnAddNewWork = (Button)findViewById(R.id.btnAddNewWork);
        btnAddNewWork.setOnClickListener(BtnAddNewWorkClick);

        Button btnAddNewEducation = (Button)findViewById(R.id.btnAddNewEducation);
        btnAddNewEducation.setOnClickListener(BtnAddNewEducationClick);

        Button btnAddNewCertificate = (Button)findViewById(R.id.btnAddNewCertificate);
        btnAddNewCertificate.setOnClickListener(BtnAddNewCertificateClick);
    }

    private View.OnClickListener BtnProfileInfoClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(SettingsActivity.this, SettingsProfileActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener BtnSizeInfoClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(SettingsActivity.this, SettingsSizeActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener BtnProfilePicturesClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(SettingsActivity.this, SettingsPicturesActivity.class);
            startActivity(intent);
        }
    };

//    private View.OnClickListener BtnProfileCVClick = new View.OnClickListener() {
//        public void onClick(View v) {
//            Intent intent = new Intent(SettingsActivity.this, SettingsCVActivity.class);
//            startActivity(intent);
//        }
//    };

    private View.OnClickListener BtnAvailableInfoClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(SettingsActivity.this, SettingsAvailabilityActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener BtnAddNewWorkClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(SettingsActivity.this, SettingsJobActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener BtnAddNewEducationClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(SettingsActivity.this, SettingsEducationActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener BtnAddNewCertificateClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(SettingsActivity.this, SettingsCertificateActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}