package com.asworks.hrmobileapp_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.EmployeeAvailability;
import com.asworks.hrmobileapp_android.model.Event;
import com.asworks.hrmobileapp_android.model.EventAdapter;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.Profession;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.asworks.hrmobileapp_android.model.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsAvailabilityActivity extends AppCompatActivity {

    private Employee currentUser;
    private SessionManager currentSession;
    private ListView listProfession;
    private ListView listWorkDays;
    private ListView listWorkType;

    ArrayList<String> workDaysListItem;
    ArrayList<String> workTypeListItem;
    ArrayList<String> ProfessionListItem;
    List<Profession> professionDbList;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_availability);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Çalışabileceğim Gün & Saatler");

        currentSession = new SessionManager(getApplicationContext());
        currentUser = currentSession.GetCurrentUser();

        workDaysListItem = new ArrayList<String>(Arrays.asList("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar"));
        workTypeListItem = new ArrayList<String>(Arrays.asList("Full Time", "Part Time"));

        listProfession = (ListView) findViewById(R.id.listProfession);
        listWorkDays = (ListView) findViewById(R.id.listWorkDays);
        listWorkType = (ListView) findViewById(R.id.listWorkType);

        IApiService professionService = IApiService.retrofit.create(IApiService.class);
        Call<ResponseBase<List<Profession>>> professionRequest = professionService.professionList();

        professionRequest.enqueue(new Callback<ResponseBase<List<Profession>>>() {
            @Override
            public void onResponse(Call<ResponseBase<List<Profession>>> call, Response<ResponseBase<List<Profession>>> response) {
                ResponseBase<List<Profession>> professionListResponse = response.body();

                if (professionListResponse.data != null) {
                    professionDbList = professionListResponse.data;
                    ProfessionListItem = new ArrayList<String>();

                    for (Profession professionItem : professionDbList) {
                        ProfessionListItem.add(professionItem.getTitle());
                    }

                    if (ProfessionListItem != null && ProfessionListItem.size() > 0)
                    {
                        ArrayAdapter<String> professionAdapter = new ArrayAdapter<String>(SettingsAvailabilityActivity.this, android.R.layout.simple_list_item_multiple_choice, ProfessionListItem);
                        listProfession.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        listProfession.setAdapter(professionAdapter);
                    }

                    if (currentUser.getProfession() != null && currentUser.getProfession().size() > 0)
                    {
                        for (Profession userProfessionItem : currentUser.getProfession()) {
                            int position = ProfessionListItem.indexOf(userProfessionItem.getTitle());

                            if (position > -1)
                            {
                                listProfession.setItemChecked(position, true);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase<List<Profession>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Hata Oluştu, Lütfen Tekrar Deneyiniz!", Toast.LENGTH_SHORT).show();
            }
        });


        ArrayAdapter<String> workDaysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, workDaysListItem);
        ArrayAdapter<String> workTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, workTypeListItem);


        listWorkDays.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listWorkDays.setAdapter(workDaysAdapter);

        listWorkType.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listWorkType.setAdapter(workTypeAdapter);

        if (currentUser.getEmployeeAvailability() != null && currentUser.getEmployeeAvailability().size() > 0)
        {
            if (currentUser.getEmployeeAvailability().get(0).getWorkDays() != "")
            {
                if (currentUser.getEmployeeAvailability().get(0).getWorkDays().length() > 1) {
                    for (String workDaysItem : currentUser.getEmployeeAvailability().get(0).getWorkDays().split(",")) {

                        listWorkDays.setItemChecked((Integer.parseInt(workDaysItem) - 1), true);

                    }
                }
                else
                {
                    listWorkDays.setItemChecked(Integer.parseInt(currentUser.getEmployeeAvailability().get(0).getWorkDays()) - 1, true);
                }
            }

            if (currentUser.getEmployeeAvailability().get(0).getWorkType() != "")
            {
                if (currentUser.getEmployeeAvailability().get(0).getWorkType().length() > 1) {
                    for (String workTypeItem : currentUser.getEmployeeAvailability().get(0).getWorkType().split(",")) {

                        listWorkType.setItemChecked(Integer.parseInt(workTypeItem) - 1, true);
                    }
                }
                else
                {
                    listWorkType.setItemChecked(Integer.parseInt(currentUser.getEmployeeAvailability().get(0).getWorkType()) - 1, true);
                }
            }
        }

        ImageButton btnAddNewAvailabilityItem = (ImageButton) findViewById(R.id.btnAddNewAvailabilityItem);
        btnAddNewAvailabilityItem.setOnClickListener(btnAddNewAvailabilityItemClick);
    }

    private View.OnClickListener btnAddNewAvailabilityItemClick = new View.OnClickListener() {
        public void onClick(View v) {

            final IApiService registerService = IApiService.retrofit.create(IApiService.class);
            final Employee registerEmployee = currentUser;

            SparseBooleanArray selectedProfessions = listProfession.getCheckedItemPositions();
            registerEmployee.setProfession(null);
            List<Profession> currentUserProfessionList = new ArrayList<Profession>();

            for(int i = 0; i < listProfession.getCount(); i++){

                if(selectedProfessions.get(i)) {

                    Profession currentProfession = professionDbList.get(i);
                    currentUserProfessionList.add(currentProfession);
                }
            }

            registerEmployee.setProfession(currentUserProfessionList);

            SparseBooleanArray selectedWorkDays = listWorkDays.getCheckedItemPositions();
            SparseBooleanArray selectedWorkType = listWorkType.getCheckedItemPositions();
            String currentUserWorkDays = "";
            String currentUserWorkType = "";
            EmployeeAvailability currentEmployeeAvailability = new EmployeeAvailability();

            if (registerEmployee.getEmployeeAvailability() != null && registerEmployee.getEmployeeAvailability().size() > 0)
            {
                currentEmployeeAvailability = registerEmployee.getEmployeeAvailability().get(0);
            }

            for(int i = 0; i < listWorkDays.getCount(); i++){

                if(selectedWorkDays.get(i)) {

                    if (i > 0)
                    {
                        currentUserWorkDays += "," + (i + 1);
                    }
                    else
                    {
                        currentUserWorkDays += (i + 1);
                    }
                }
            }

            if (currentUserWorkDays.length() > 0)
            {
                currentEmployeeAvailability.setWorkDays(currentUserWorkDays);
            }

            for(int i = 0; i < listWorkType.getCount(); i++){

                if(selectedWorkType.get(i)) {

                    if (i > 0)
                    {
                        currentUserWorkType += "," + (i + 1);
                    }
                    else
                    {
                        currentUserWorkType += (i + 1);
                    }
                }
            }

            if (currentUserWorkType.length() > 0)
            {
                currentEmployeeAvailability.setWorkType(currentUserWorkType);
            }

            List<EmployeeAvailability> availabilityList = new ArrayList<EmployeeAvailability>();
            availabilityList.add(currentEmployeeAvailability);
            registerEmployee.setEmployeeAvailability(availabilityList);

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

                        Intent mainPage = new Intent(SettingsAvailabilityActivity.this, SettingsActivity.class);
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
    };

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
