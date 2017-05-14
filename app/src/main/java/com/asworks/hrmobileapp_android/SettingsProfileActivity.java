package com.asworks.hrmobileapp_android;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.asworks.hrmobileapp_android.model.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsProfileActivity extends AppCompatActivity {

    private EditText txtProfileName;
    private EditText txtProfileSurname;
    private EditText txtProfileMail;
    private EditText txtProfilePassword;
    private EditText txtProfileTCNo;
    private EditText txtProfilePhone;
    private EditText txtProfileAddress;
    private EditText txtProfileBirthDate;
    private EditText txtProfileBirthPlace;
    private Spinner genderSelect;
    private Spinner driverLicenseSelect;
    private Spinner activeDriverSelect;
    private int year, month, day;
    private Employee currentUser;
    private SessionManager currentSession;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil Bilgilerim");
        currentSession = new SessionManager(getApplicationContext());
        currentUser = currentSession.GetCurrentUser();

        txtProfileName = (EditText)findViewById(R.id.txtProfileName);
        txtProfileName.setText(currentUser.getFirstName());

        txtProfileSurname = (EditText)findViewById(R.id.txtProfileSurname);
        txtProfileSurname.setText(currentUser.getLastName());

        txtProfileMail = (EditText)findViewById(R.id.txtProfileMail);
        txtProfileMail.setText(currentUser.getMailAddress());

        txtProfilePassword = (EditText)findViewById(R.id.txtProfilePassword);

        txtProfileTCNo = (EditText)findViewById(R.id.txtProfileTCNo);
        txtProfileTCNo.setText(currentUser.getTCIdentityNo());

        txtProfilePhone = (EditText)findViewById(R.id.txtProfilePhone);
        txtProfilePhone.setText(currentUser.getPhone());

        txtProfileAddress = (EditText)findViewById(R.id.txtProfileAddress);
        txtProfileAddress.setText(currentUser.getAddress());

        txtProfileBirthDate = (EditText)findViewById(R.id.txtProfileBirthDate);
        txtProfileBirthDate.setText(currentUser.getBirthDate());

        txtProfileBirthPlace = (EditText)findViewById(R.id.txtProfileBirthPlace);
        txtProfileBirthPlace.setText(currentUser.getBirthPlace());

        final ImageButton btnUpdateProfile = (ImageButton)findViewById(R.id.btnUpdateProfile);
        btnUpdateProfile.setOnClickListener(btnUpdateProfileClick);
        txtProfileBirthDate.setOnClickListener(txtProfileBirthDateClick);
        setSpinners();
    }

    private void setSpinners()
    {
        genderSelect = (Spinner) findViewById(R.id.genderSelect);
        driverLicenseSelect = (Spinner) findViewById(R.id.driverLicenseSelect);
        activeDriverSelect = (Spinner) findViewById(R.id.activeDriverSelect);

        List<String> genderSelectItemList = new ArrayList<String>();
        genderSelectItemList.add("Erkek");
        genderSelectItemList.add("Kadın");

        List<String> licenseSelectItemList = new ArrayList<String>();
        licenseSelectItemList.add("Hayır");
        licenseSelectItemList.add("Evet");

        ArrayAdapter<String> genderDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderSelectItemList);
        ArrayAdapter<String> licenseDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, licenseSelectItemList);


        genderDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        licenseDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int currentUserGender = currentUser.getGender() > 0 ? currentUser.getGender() - 1 : 0;
        genderSelect.setAdapter(genderDataAdapter);
        genderSelect.setSelection(currentUserGender);

        int currentUserLicense = currentUser.getHasDriverLicense() ? 1 : 0;
        driverLicenseSelect.setAdapter(licenseDataAdapter);
        driverLicenseSelect.setSelection(currentUserLicense);

        int currentUserActiveDriver = currentUser.getActiveCarDriver() ? 1 : 0;
        activeDriverSelect.setAdapter(licenseDataAdapter);
        activeDriverSelect.setSelection(currentUserActiveDriver);
    }

    private View.OnClickListener txtProfileBirthDateClick = new View.OnClickListener() {
        public void onClick(View v) {
            Calendar currentDate = Calendar.getInstance();
            year = currentDate.get(Calendar.YEAR);
            month = currentDate.get(Calendar.MONTH);
            day = currentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker = new DatePickerDialog(SettingsProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    txtProfileBirthDate.setText(new StringBuilder().append(selectedday).append("-").append(selectedmonth + 1).append("-").append(selectedyear));
                }
            }, year, month + 1, day);
            mDatePicker.setTitle("Doğum Tarihiniz");
            mDatePicker.show();  }
    };

    private View.OnClickListener btnUpdateProfileClick = new View.OnClickListener() {
        public void onClick(View v) {
            if (validateRegisterForm())
            {
                IApiService registerService = IApiService.retrofit.create(IApiService.class);

                Employee registerEmployee = new Employee();
                registerEmployee.setID(currentUser.getID());
                registerEmployee.setStatus(1);
                registerEmployee.setFirstName(txtProfileName.getText().toString());
                registerEmployee.setLastName(txtProfileSurname.getText().toString());
                registerEmployee.setMailAddress(txtProfileMail.getText().toString());
                registerEmployee.setPassword(txtProfilePassword.getText().toString());
                registerEmployee.setTCIdentityNo(txtProfileTCNo.getText().toString());
                registerEmployee.setPhone(txtProfilePhone.getText().toString());
                registerEmployee.setAddress(txtProfileAddress.getText().toString());
                registerEmployee.setBirthDate(txtProfileBirthDate.getText().toString());
                registerEmployee.setBirthPlace(txtProfileBirthPlace.getText().toString());
                registerEmployee.setGender(genderSelect.getSelectedItemPosition() + 1);
                registerEmployee.setHasDriverLicense(driverLicenseSelect.getSelectedItemPosition() != 0);
                registerEmployee.setActiveCarDriver(activeDriverSelect.getSelectedItemPosition() != 0);

                Call<ResponseBase<Employee>> registerRequest = registerService.register(registerEmployee);

                registerRequest.enqueue(new Callback<ResponseBase<Employee>>() {
                    @Override
                    public void onResponse(Call<ResponseBase<Employee>> call, Response<ResponseBase<Employee>> response) {
                        ResponseBase<Employee> registerResponse = response.body();

                        if (registerResponse.success)
                        {
                            currentUser.setFirstName(txtProfileName.getText().toString());
                            currentUser.setLastName(txtProfileSurname.getText().toString());
                            currentUser.setMailAddress(txtProfileMail.getText().toString());
                            currentUser.setPassword(txtProfilePassword.getText().toString());
                            currentUser.setTCIdentityNo(txtProfileTCNo.getText().toString());
                            currentUser.setPhone(txtProfilePhone.getText().toString());
                            currentUser.setAddress(txtProfileAddress.getText().toString());
                            currentUser.setBirthDate(txtProfileBirthDate.getText().toString());
                            currentUser.setBirthPlace(txtProfileBirthPlace.getText().toString());
                            currentUser.setGender(genderSelect.getSelectedItemPosition() + 1);
                            currentUser.setHasDriverLicense(driverLicenseSelect.getSelectedItemPosition() != 0);
                            currentUser.setActiveCarDriver(activeDriverSelect.getSelectedItemPosition() != 0);
                            currentSession.SetCurrentUser(currentUser);

                            Intent mainPage = new Intent(SettingsProfileActivity.this, SettingsActivity.class);
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

        if(txtProfileName.getText().toString().length() == 0 )
        {txtProfileName.setError( "Lütfen Adınızı Giriniz!" );
            result = false;}

        if(txtProfileSurname.getText().toString().length() == 0 )
        {txtProfileSurname.setError( "Lütfen Soyadınızı Giriniz!" );
            result = false;}

        if(txtProfileMail.getText().toString().length() == 0 )
        {txtProfileMail.setError( "Lütfen Mail Adresinizi Giriniz!" );
            result = false;}

        if(txtProfilePassword.getText().toString().length() == 0 )
        {txtProfilePassword.setError( "Lütfen Şifrenizi Giriniz!" );
            result = false;}

        if(txtProfileTCNo.getText().toString().length() == 0 )
        {txtProfileTCNo.setError( "Lütfen TC No Giriniz!" );
            result = false;}

        if(txtProfilePhone.getText().toString().length() == 0 )
        {txtProfilePhone.setError( "Lütfen Telefon Numaranızı Giriniz!" );
            result = false;}

        if(txtProfileAddress.getText().toString().length() == 0 )
        {txtProfileAddress.setError( "Lütfen Adresinizi Giriniz!" );
            result = false;}

        if(txtProfileBirthDate.getText().toString().length() == 0 )
        {txtProfileBirthDate.setError( "Lütfen Doğum Tarihinizi Giriniz!" );
            result = false;}

        if(txtProfileBirthPlace.getText().toString().length() == 0 )
        {txtProfileBirthPlace.setError( "Lütfen Doğum Yerinizi Giriniz!" );
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