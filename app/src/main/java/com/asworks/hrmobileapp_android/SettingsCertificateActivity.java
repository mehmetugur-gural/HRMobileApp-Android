package com.asworks.hrmobileapp_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.EmployeeCertificateAndLanguage;
import com.asworks.hrmobileapp_android.model.EmployeeJobExperience;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.ListViewCertificateAdapter;
import com.asworks.hrmobileapp_android.model.ListViewJobAdapter;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.asworks.hrmobileapp_android.model.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsCertificateActivity extends AppCompatActivity {

    private Employee currentUser;
    private SessionManager currentSession;
    private ListView lvCertificateList;
    AlertDialog addNewCertificateDialog;
    List<EmployeeCertificateAndLanguage> certificateList;
    EditText txtNewCertificateTitle;
    EditText txtNewCertificateOrganization;
    ImageButton btnSaveNewCertificateItem;
    TextView lblAddNewCertificateItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_certificate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sertifika Bilgilerim");

        currentSession = new SessionManager(getApplicationContext());
        currentUser = currentSession.GetCurrentUser();
        lvCertificateList = (ListView)findViewById(R.id.lvCertificateList);
        certificateList = currentUser.getEmployeeCertificateAndLanguage();
        ListViewCertificateAdapter certficiateAdapter = new ListViewCertificateAdapter(this, certificateList);
        lvCertificateList.setAdapter(certficiateAdapter);

        final ImageButton btnAddNewCertificateItem = (ImageButton) findViewById(R.id.btnAddNewCertificateItem);
        lblAddNewCertificateItem = (TextView) findViewById(R.id.lblAddNewCertificateItem);
        btnAddNewCertificateItem.setOnClickListener(btnAddNewCertificateItemClick);

        lvCertificateList.setOnItemClickListener(lvCertificateListItemClick);

        if (certificateList != null && certificateList.size() > 2) {
            btnAddNewCertificateItem.setVisibility(View.GONE);
            lblAddNewCertificateItem.setVisibility(View.GONE);
        }
    }

    private ListView.OnItemClickListener lvCertificateListItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            View addNewCertificateView = getLayoutInflater().inflate(R.layout.settingscertificate_newitem, null);

            txtNewCertificateTitle = (EditText)addNewCertificateView.findViewById(R.id.txtNewCertificateTitle);
            txtNewCertificateTitle.setText(certificateList.get(position).getTitle().toString());

            txtNewCertificateOrganization = (EditText)addNewCertificateView.findViewById(R.id.txtNewCertificateOrganization);
            txtNewCertificateOrganization.setText(certificateList.get(position).getOrganizationName().toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsCertificateActivity.this);
            builder.setTitle(Html.fromHtml("<font color='#51C4D4'>Sertifika Bilgileri Güncelle</font>"));
            builder.setView(addNewCertificateView);
            addNewCertificateDialog = builder.create();
            addNewCertificateDialog.show();

            btnSaveNewCertificateItem = (ImageButton)addNewCertificateView.findViewById(R.id.btnSaveNewCertificateItem);
            btnSaveNewCertificateItem.setTag(position);
            btnSaveNewCertificateItem.setOnClickListener(btnSaveNewCertificateItemClick);
        }
    };

    private View.OnClickListener btnAddNewCertificateItemClick = new View.OnClickListener() {
        public void onClick(View v) {

            View addNewCertificateView = getLayoutInflater().inflate(R.layout.settingscertificate_newitem, null);

            txtNewCertificateTitle = (EditText)addNewCertificateView.findViewById(R.id.txtNewCertificateTitle);
            txtNewCertificateOrganization = (EditText)addNewCertificateView.findViewById(R.id.txtNewCertificateOrganization);

            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsCertificateActivity.this);
            builder.setView(addNewCertificateView);
            addNewCertificateDialog = builder.create();
            addNewCertificateDialog.show();

            btnSaveNewCertificateItem = (ImageButton)addNewCertificateView.findViewById(R.id.btnSaveNewCertificateItem);
            btnSaveNewCertificateItem.setOnClickListener(btnSaveNewCertificateItemClick);
        }
    };



    private View.OnClickListener btnSaveNewCertificateItemClick = new View.OnClickListener() {
        public void onClick(View v) {

            if (validateRegisterForm())
            {
                IApiService registerService = IApiService.retrofit.create(IApiService.class);

                final Employee registerEmployee = currentUser;

                if (btnSaveNewCertificateItem.getTag() != null)
                {
                    EmployeeCertificateAndLanguage currentCertificate = registerEmployee.getEmployeeCertificateAndLanguage().get(Integer.parseInt(btnSaveNewCertificateItem.getTag().toString()));
                    currentCertificate.setTitle(txtNewCertificateTitle.getText().toString());
                    currentCertificate.setOrganizationName(txtNewCertificateOrganization.getText().toString());
                    registerEmployee.getEmployeeCertificateAndLanguage().set(Integer.parseInt(btnSaveNewCertificateItem.getTag().toString()), currentCertificate);
                }
                else
                {
                    EmployeeCertificateAndLanguage currentCertificate = new EmployeeCertificateAndLanguage();
                    currentCertificate.setTitle(txtNewCertificateTitle.getText().toString());
                    currentCertificate.setOrganizationName(txtNewCertificateOrganization.getText().toString());
                    currentCertificate.setIsActive(true);
                    registerEmployee.getEmployeeCertificateAndLanguage().add(currentCertificate);
                }

                registerEmployee.setPassword("");
                Call<ResponseBase<Employee>> registerRequest = registerService.register(registerEmployee);
                registerEmployee.setPassword(currentUser.getPassword());

                registerRequest.enqueue(new Callback<ResponseBase<Employee>>() {
                    @Override
                    public void onResponse(Call<ResponseBase<Employee>> call, Response<ResponseBase<Employee>> response) {
                        ResponseBase<Employee> registerResponse = response.body();

                        if (registerResponse.success)
                        {
                            currentSession.SetCurrentUser(registerEmployee);

                            Intent mainPage = new Intent(SettingsCertificateActivity.this, SettingsActivity.class);
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

        if(txtNewCertificateTitle.getText().toString().length() == 0 )
        {txtNewCertificateTitle.setError( "Lütfen Sertifika Adını Giriniz!" );
            result = false;}

        if(txtNewCertificateOrganization.getText().toString().length() == 0 )
        {txtNewCertificateOrganization.setError( "Lütfen Kurum Adını Giriniz!" );
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