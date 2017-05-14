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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.EmployeeJobExperience;
import com.asworks.hrmobileapp_android.model.EventAttendAdapter;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.ListViewJobAdapter;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.asworks.hrmobileapp_android.model.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsJobActivity extends AppCompatActivity {

    private Employee currentUser;
    private SessionManager currentSession;
    private ListView lvJobList;
    AlertDialog addNewJobDialog;
    List<EmployeeJobExperience> jobExperienceList;
    EditText txtNewJobCompany;
    EditText txtNewJobTitle;
    EditText txtNewJobYear;
    EditText txtNewJobMonth;
    EditText txtNewJobSalary;
    ImageButton btnSaveNewJobItem;
    TextView lblAttendEvent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_job);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("İş Deneyimlerim");

        currentSession = new SessionManager(getApplicationContext());
        currentUser = currentSession.GetCurrentUser();
        lvJobList = (ListView)findViewById(R.id.lvJobList);
        jobExperienceList = currentUser.getEmployeeJobExperience();
        ListViewJobAdapter jobAdapter = new ListViewJobAdapter(this, jobExperienceList);
        lvJobList.setAdapter(jobAdapter);

        final ImageButton btnAddNewJobItem = (ImageButton) findViewById(R.id.btnAddNewJobItem);
        lblAttendEvent = (TextView) findViewById(R.id.lblAttendEvent);
        btnAddNewJobItem.setOnClickListener(btnAddNewJobItemClick);

        lvJobList.setOnItemClickListener(lvJobListItemClick);

        if (jobExperienceList != null && jobExperienceList.size() > 2) {
            btnAddNewJobItem.setVisibility(View.GONE);
            lblAttendEvent.setVisibility(View.GONE);
        }
    }

    private ListView.OnItemClickListener lvJobListItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            View addNewJobView = getLayoutInflater().inflate(R.layout.settingsjob_newitem, null);

            txtNewJobCompany = (EditText)addNewJobView.findViewById(R.id.txtNewJobCompany);
            txtNewJobCompany.setText(jobExperienceList.get(position).getCompanyName());

            txtNewJobTitle = (EditText)addNewJobView.findViewById(R.id.txtNewJobTitle);
            txtNewJobTitle.setText(jobExperienceList.get(position).getTitle());

            txtNewJobYear = (EditText)addNewJobView.findViewById(R.id.txtNewJobYear);
            txtNewJobYear.setText(jobExperienceList.get(position).getWorkYear().toString());

            txtNewJobMonth = (EditText)addNewJobView.findViewById(R.id.txtNewJobMonth);
            txtNewJobMonth.setText(jobExperienceList.get(position).getWorkMonth().toString());

            txtNewJobSalary = (EditText)addNewJobView.findViewById(R.id.txtNewJobSalary);
            txtNewJobSalary.setText(jobExperienceList.get(position).getSalary().toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsJobActivity.this);
            builder.setTitle(Html.fromHtml("<font color='#51C4D4'>İş Deneyimi Güncelle</font>"));
            builder.setView(addNewJobView);
            addNewJobDialog = builder.create();
            addNewJobDialog.show();

            btnSaveNewJobItem = (ImageButton)addNewJobView.findViewById(R.id.btnSaveNewJobItem);
            btnSaveNewJobItem.setTag(position);
            btnSaveNewJobItem.setOnClickListener(btnSaveNewJobItemClick);
        }
    };

    private View.OnClickListener btnAddNewJobItemClick = new View.OnClickListener() {
        public void onClick(View v) {

            View addNewJobView = getLayoutInflater().inflate(R.layout.settingsjob_newitem, null);

            txtNewJobCompany = (EditText)addNewJobView.findViewById(R.id.txtNewJobCompany);
            txtNewJobTitle = (EditText)addNewJobView.findViewById(R.id.txtNewJobTitle);
            txtNewJobYear = (EditText)addNewJobView.findViewById(R.id.txtNewJobYear);
            txtNewJobMonth = (EditText)addNewJobView.findViewById(R.id.txtNewJobMonth);
            txtNewJobSalary = (EditText)addNewJobView.findViewById(R.id.txtNewJobSalary);

            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsJobActivity.this);
            builder.setView(addNewJobView);
            addNewJobDialog = builder.create();
            addNewJobDialog.show();

            btnSaveNewJobItem = (ImageButton)addNewJobView.findViewById(R.id.btnSaveNewJobItem);
            btnSaveNewJobItem.setOnClickListener(btnSaveNewJobItemClick);
        }
    };



    private View.OnClickListener btnSaveNewJobItemClick = new View.OnClickListener() {
        public void onClick(View v) {

            if (validateRegisterForm())
            {
                IApiService registerService = IApiService.retrofit.create(IApiService.class);

                final Employee registerEmployee = currentUser;

                if (btnSaveNewJobItem.getTag() != null)
                {
                    EmployeeJobExperience currentJob = registerEmployee.getEmployeeJobExperience().get(Integer.parseInt(btnSaveNewJobItem.getTag().toString()));
                    currentJob.setCompanyName(txtNewJobCompany.getText().toString());
                    currentJob.setTitle(txtNewJobTitle.getText().toString());
                    currentJob.setWorkYear(Integer.parseInt(txtNewJobYear.getText().toString()));
                    currentJob.setWorkMonth(Integer.parseInt(txtNewJobMonth.getText().toString()));
                    currentJob.setSalary(Integer.parseInt(txtNewJobSalary.getText().toString()));
                    registerEmployee.getEmployeeJobExperience().set(Integer.parseInt(btnSaveNewJobItem.getTag().toString()), currentJob);
                }
                else
                {
                    EmployeeJobExperience currentJob = new EmployeeJobExperience();
                    currentJob.setCompanyName(txtNewJobCompany.getText().toString());
                    currentJob.setTitle(txtNewJobTitle.getText().toString());
                    currentJob.setWorkYear(Integer.parseInt(txtNewJobYear.getText().toString()));
                    currentJob.setWorkMonth(Integer.parseInt(txtNewJobMonth.getText().toString()));
                    currentJob.setSalary(Integer.parseInt(txtNewJobSalary.getText().toString()));
                    currentJob.setIsActive(true);
                    registerEmployee.getEmployeeJobExperience().add(currentJob);
                }

                Call<ResponseBase<Employee>> registerRequest = registerService.register(registerEmployee);

                registerRequest.enqueue(new Callback<ResponseBase<Employee>>() {
                    @Override
                    public void onResponse(Call<ResponseBase<Employee>> call, Response<ResponseBase<Employee>> response) {
                        ResponseBase<Employee> registerResponse = response.body();

                        if (registerResponse.success)
                        {
                            currentSession.SetCurrentUser(registerEmployee);

                            Intent mainPage = new Intent(SettingsJobActivity.this, SettingsActivity.class);
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

        if(txtNewJobTitle.getText().toString().length() == 0 )
        {txtNewJobTitle.setError( "Lütfen Ünvanınızı Giriniz!" );
            result = false;}

        if(txtNewJobCompany.getText().toString().length() == 0 )
        {txtNewJobCompany.setError( "Lütfen Firmanızı Giriniz!" );
            result = false;}

        if(txtNewJobYear.getText().toString().length() == 0 )
        {txtNewJobYear.setError( "Lütfen Toplam Çalışma Yılınızı Giriniz!" );
            result = false;}

        if(txtNewJobMonth.getText().toString().length() == 0 )
        {txtNewJobMonth.setError( "Lütfen Toplam Çalışma Ayınızı Giriniz!" );
            result = false;}

        if(txtNewJobSalary.getText().toString().length() == 0 )
        {txtNewJobSalary.setError( "Lütfen Ücret Bilginizi Giriniz!" );
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
