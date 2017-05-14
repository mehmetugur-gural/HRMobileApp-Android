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
import com.asworks.hrmobileapp_android.model.EmployeeEducation;
import com.asworks.hrmobileapp_android.model.EmployeeJobExperience;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.ListViewEducationAdapter;
import com.asworks.hrmobileapp_android.model.ListViewJobAdapter;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.asworks.hrmobileapp_android.model.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsEducationActivity extends AppCompatActivity {

    private Employee currentUser;
    private SessionManager currentSession;
    private ListView lvEducationList;
    AlertDialog addNewEducationDialog;
    List<EmployeeEducation> educationList;
    EditText txtNewEducationOrganization;
    EditText txtNewEducationInstitute;
    EditText txtNewEducationDegree;
    ImageButton btnSaveNewEducationItem;
    TextView lblSaveEducation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_education);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Eğitim Bilgilerim");

        currentSession = new SessionManager(getApplicationContext());
        currentUser = currentSession.GetCurrentUser();
        lvEducationList = (ListView)findViewById(R.id.lvJobList);
        educationList = currentUser.getEmployeeEducation();
        ListViewEducationAdapter educationAdapter = new ListViewEducationAdapter(this, educationList);
        lvEducationList.setAdapter(educationAdapter);

        final ImageButton btnAddNewEducationItem = (ImageButton) findViewById(R.id.btnAddNewEducationItem);
        lblSaveEducation = (TextView) findViewById(R.id.lblAttendEvent);
        btnAddNewEducationItem.setOnClickListener(btnAddNewEducationItemClick);

        lvEducationList.setOnItemClickListener(lvEducationListItemClick);

        if (educationList != null && educationList.size() > 2) {
            btnAddNewEducationItem.setVisibility(View.GONE);
            lblSaveEducation.setVisibility(View.GONE);
        }
    }

    private ListView.OnItemClickListener lvEducationListItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            View addNewEducationView = getLayoutInflater().inflate(R.layout.settingseducation_newitem, null);

            txtNewEducationOrganization = (EditText)addNewEducationView.findViewById(R.id.txtNewEducationOrganization);
            txtNewEducationOrganization.setText(educationList.get(position).getOrganizationName().toString());

            txtNewEducationInstitute = (EditText)addNewEducationView.findViewById(R.id.txtNewEducationInstitute);
            txtNewEducationInstitute.setText(educationList.get(position).getInstitute().toString());

            txtNewEducationDegree = (EditText)addNewEducationView.findViewById(R.id.txtNewEducationDegree);
            txtNewEducationDegree.setText(educationList.get(position).getDegree().toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsEducationActivity.this);
            builder.setTitle(Html.fromHtml("<font color='#51C4D4'>Eğitim Bilgisi Güncelle</font>"));
            builder.setView(addNewEducationView);
            addNewEducationDialog = builder.create();
            addNewEducationDialog.show();

            btnSaveNewEducationItem = (ImageButton)addNewEducationDialog.findViewById(R.id.btnSaveNewEducationItem);
            btnSaveNewEducationItem.setTag(position);
            btnSaveNewEducationItem.setOnClickListener(btnSaveNewEducationItemClick);
        }
    };

    private View.OnClickListener btnAddNewEducationItemClick = new View.OnClickListener() {
        public void onClick(View v) {

            View addNewEducationView = getLayoutInflater().inflate(R.layout.settingseducation_newitem, null);

            txtNewEducationOrganization = (EditText)addNewEducationView.findViewById(R.id.txtNewEducationOrganization);
            txtNewEducationInstitute = (EditText)addNewEducationView.findViewById(R.id.txtNewEducationInstitute);
            txtNewEducationDegree = (EditText)addNewEducationView.findViewById(R.id.txtNewEducationDegree);

            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsEducationActivity.this);
            builder.setView(addNewEducationView);
            addNewEducationDialog = builder.create();
            addNewEducationDialog.show();

            btnSaveNewEducationItem = (ImageButton)addNewEducationView.findViewById(R.id.btnSaveNewEducationItem);
            btnSaveNewEducationItem.setOnClickListener(btnSaveNewEducationItemClick);
        }
    };


    private View.OnClickListener btnSaveNewEducationItemClick = new View.OnClickListener() {
        public void onClick(View v) {

            if (validateRegisterForm())
            {
                IApiService registerService = IApiService.retrofit.create(IApiService.class);

                final Employee registerEmployee = currentUser;

                if (btnSaveNewEducationItem.getTag() != null)
                {
                    EmployeeEducation currentEducation = registerEmployee.getEmployeeEducation().get(Integer.parseInt(btnSaveNewEducationItem.getTag().toString()));
                    currentEducation.setOrganizationName(txtNewEducationOrganization.getText().toString());
                    currentEducation.setInstitute(txtNewEducationInstitute.getText().toString());
                    currentEducation.setDegree(Integer.parseInt(txtNewEducationDegree.getText().toString()));
                    registerEmployee.getEmployeeEducation().set(Integer.parseInt(btnSaveNewEducationItem.getTag().toString()), currentEducation);
                }
                else
                {
                    EmployeeEducation currentEducation = new EmployeeEducation();
                    currentEducation.setOrganizationName(txtNewEducationOrganization.getText().toString());
                    currentEducation.setInstitute(txtNewEducationInstitute.getText().toString());
                    currentEducation.setDegree(Integer.parseInt(txtNewEducationDegree.getText().toString()));
                    currentEducation.setIsActive(true);
                    registerEmployee.getEmployeeEducation().add(currentEducation);
                }

                Call<ResponseBase<Employee>> registerRequest = registerService.register(registerEmployee);

                registerRequest.enqueue(new Callback<ResponseBase<Employee>>() {
                    @Override
                    public void onResponse(Call<ResponseBase<Employee>> call, Response<ResponseBase<Employee>> response) {
                        ResponseBase<Employee> registerResponse = response.body();

                        if (registerResponse.success)
                        {
                            currentSession.SetCurrentUser(registerEmployee);

                            Intent mainPage = new Intent(SettingsEducationActivity.this, SettingsActivity.class);
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

        if(txtNewEducationOrganization.getText().toString().length() == 0 )
        {txtNewEducationOrganization.setError( "Lütfen Okul Adını Giriniz!" );
            result = false;}

        if(txtNewEducationInstitute.getText().toString().length() == 0 )
        {txtNewEducationInstitute.setError( "Lütfen Bölüm Adını Giriniz!" );
            result = false;}

        if(txtNewEducationDegree.getText().toString().length() == 0 )
        {txtNewEducationDegree.setError( "Lütfen Derece Bilginizi Giriniz!" );
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