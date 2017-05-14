package com.asworks.hrmobileapp_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.asworks.hrmobileapp_android.model.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsSizeActivity extends AppCompatActivity {

    private EditText txtProfileHeight;
    private EditText txtProfileWeight;
    private EditText txtProfileHairColor;
    private EditText txtProfileEyeColor;
    private EditText txtProfileUpperBodySize;
    private EditText txtProfileLowerBodySize;
    private EditText txtProfileChestSize;
    private EditText txtProfileWaistSize;
    private EditText txtProfileHipSize;
    private EditText txtProfileShoeSize;
    private EditText txtProfileJacketSize;
    private EditText txtProfilePantSize;
    private EditText txtProfileJeanSize;
    private EditText txtProfileSkirtSize;
    private Employee currentUser;
    private SessionManager currentSession;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_size);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ölçü Bilgilerim");
        currentSession = new SessionManager(getApplicationContext());
        currentUser = currentSession.GetCurrentUser();

        txtProfileHeight = (EditText)findViewById(R.id.txtProfileHeight);
        txtProfileHeight.setText(String.valueOf(currentUser.getHeight()));

        txtProfileWeight = (EditText)findViewById(R.id.txtProfileWeight);
        txtProfileWeight.setText(String.valueOf(currentUser.getWeight()));

        txtProfileHairColor = (EditText)findViewById(R.id.txtProfileHairColor);
        txtProfileHairColor.setText(currentUser.getHairColor());

        txtProfileEyeColor = (EditText)findViewById(R.id.txtProfileEyeColor);
        txtProfileEyeColor.setText(currentUser.getEyeColor());

        txtProfileUpperBodySize = (EditText)findViewById(R.id.txtProfileUpperBodySize);
        txtProfileUpperBodySize.setText(currentUser.getUpperBodySize());

        txtProfileLowerBodySize = (EditText)findViewById(R.id.txtProfileLowerBodySize);
        txtProfileLowerBodySize.setText(currentUser.getLowerBodySize());

        txtProfileChestSize = (EditText)findViewById(R.id.txtProfileChestSize);
        txtProfileChestSize.setText(currentUser.getChestSize());

        txtProfileWaistSize = (EditText)findViewById(R.id.txtProfileWaistSize);
        txtProfileWaistSize.setText(currentUser.getWaistSize());

        txtProfileHipSize = (EditText)findViewById(R.id.txtProfileHipSize);
        txtProfileHipSize.setText(currentUser.getHipSize());

        txtProfileShoeSize = (EditText)findViewById(R.id.txtProfileShoeSize);
        txtProfileShoeSize.setText(currentUser.getShoeSize());

        txtProfileJacketSize = (EditText)findViewById(R.id.txtProfileJacketSize);
        txtProfileJacketSize.setText(currentUser.getJacketSize());

        txtProfilePantSize = (EditText)findViewById(R.id.txtProfilePantSize);
        txtProfilePantSize.setText(currentUser.getPantSize());

        txtProfileJeanSize = (EditText)findViewById(R.id.txtProfileJeanSize);
        txtProfileJeanSize.setText(currentUser.getJeanSize());

        txtProfileSkirtSize = (EditText)findViewById(R.id.txtProfileSkirtSize);
        txtProfileSkirtSize.setText(currentUser.getSkirtSize());

        final ImageButton btnUpdateProfile = (ImageButton)findViewById(R.id.btnUpdateProfile);
        btnUpdateProfile.setOnClickListener(btnUpdateProfileClick);
    }


    private View.OnClickListener btnUpdateProfileClick = new View.OnClickListener() {
        public void onClick(View v) {
            if (validateRegisterForm())
            {
                IApiService registerService = IApiService.retrofit.create(IApiService.class);

                Employee registerEmployee = new Employee();
                registerEmployee.setID(currentUser.getID());
                registerEmployee.setStatus(1);
                registerEmployee.setHeight(Integer.parseInt(txtProfileHeight.getText().toString()));
                registerEmployee.setWeight(Integer.parseInt(txtProfileWeight.getText().toString()));
                registerEmployee.setEyeColor(txtProfileEyeColor.getText().toString());
                registerEmployee.setHairColor(txtProfileHairColor.getText().toString());
                registerEmployee.setUpperBodySize(txtProfileUpperBodySize.getText().toString());
                registerEmployee.setLowerBodySize(txtProfileLowerBodySize.getText().toString());
                registerEmployee.setChestSize(txtProfileChestSize.getText().toString());
                registerEmployee.setWaistSize(txtProfileWaistSize.getText().toString());
                registerEmployee.setHipSize(txtProfileHipSize.getText().toString());
                registerEmployee.setShoeSize(txtProfileShoeSize.getText().toString());
                registerEmployee.setJacketSize(txtProfileJacketSize.getText().toString());
                registerEmployee.setPantSize(txtProfilePantSize.getText().toString());
                registerEmployee.setJeanSize(txtProfileJeanSize.getText().toString());
                registerEmployee.setSkirtSize(txtProfileSkirtSize.getText().toString());

                registerEmployee.setFirstName(currentUser.getFirstName());
                registerEmployee.setLastName(currentUser.getLastName());
                registerEmployee.setMailAddress(currentUser.getMailAddress());
                registerEmployee.setTCIdentityNo(currentUser.getTCIdentityNo());
                registerEmployee.setPhone(currentUser.getPhone());
                registerEmployee.setAddress(currentUser.getAddress());
                registerEmployee.setBirthDate(currentUser.getBirthDate());
                registerEmployee.setBirthPlace(currentUser.getBirthPlace());
                registerEmployee.setGender(currentUser.getGender());
                registerEmployee.setHasDriverLicense(currentUser.getHasDriverLicense());
                registerEmployee.setActiveCarDriver(currentUser.getActiveCarDriver());

                Call<ResponseBase<Employee>> registerRequest = registerService.register(registerEmployee);

                registerRequest.enqueue(new Callback<ResponseBase<Employee>>() {
                    @Override
                    public void onResponse(Call<ResponseBase<Employee>> call, Response<ResponseBase<Employee>> response) {
                        ResponseBase<Employee> registerResponse = response.body();

                        if (registerResponse.success)
                        {
                            currentUser.setHeight(Integer.parseInt(txtProfileHeight.getText().toString()));
                            currentUser.setWeight(Integer.parseInt(txtProfileWeight.getText().toString()));
                            currentUser.setEyeColor(txtProfileEyeColor.getText().toString());
                            currentUser.setHairColor(txtProfileHairColor.getText().toString());
                            currentUser.setUpperBodySize(txtProfileUpperBodySize.getText().toString());
                            currentUser.setLowerBodySize(txtProfileLowerBodySize.getText().toString());
                            currentUser.setChestSize(txtProfileChestSize.getText().toString());
                            currentUser.setWaistSize(txtProfileWaistSize.getText().toString());
                            currentUser.setHipSize(txtProfileHipSize.getText().toString());
                            currentUser.setShoeSize(txtProfileShoeSize.getText().toString());
                            currentUser.setJacketSize(txtProfileJacketSize.getText().toString());
                            currentUser.setPantSize(txtProfilePantSize.getText().toString());
                            currentUser.setJeanSize(txtProfileJeanSize.getText().toString());
                            currentUser.setSkirtSize(txtProfileSkirtSize.getText().toString());
                            currentSession.SetCurrentUser(currentUser);

                            Intent mainPage = new Intent(SettingsSizeActivity.this, SettingsActivity.class);
                            startActivity(mainPage);
                            Toast.makeText(getApplicationContext(), "Üyelik Bilgileriniz Güncellendi.", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), registerResponse.message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBase<Employee>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Hata Oluştu, Lütfen Tekrar Deneyiniz!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    public boolean validateRegisterForm()
    {
        boolean result = true;

        if(txtProfileHeight.getText().toString().length() == 0 )
        {txtProfileHeight.setError( "Lütfen Boyunuzu Giriniz!" );
            result = false;}

        if(txtProfileWeight.getText().toString().length() == 0 )
        {txtProfileWeight.setError( "Lütfen Kilonuzu Giriniz!" );
            result = false;}

        if(txtProfileEyeColor.getText().toString().length() == 0 )
        {txtProfileEyeColor.setError( "Lütfen Göz Renginizi Giriniz!" );
            result = false;}

        if(txtProfileHairColor.getText().toString().length() == 0 )
        {txtProfileHairColor.setError( "Lütfen Saç Renginizi Giriniz!" );
            result = false;}

        if(txtProfileUpperBodySize.getText().toString().length() == 0 )
        {txtProfileUpperBodySize.setError( "Lütfen Üst Beden Ölçünüzü Giriniz!" );
            result = false;}

        if(txtProfileLowerBodySize.getText().toString().length() == 0 )
        {txtProfileLowerBodySize.setError( "Lütfen Alt Beden Ölçünüzü Giriniz!" );
            result = false;}

        return  result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}