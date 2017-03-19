package com.asworks.hrmobileapp_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.Event;
import com.asworks.hrmobileapp_android.model.EventAttendAdapter;
import com.asworks.hrmobileapp_android.model.EventProfessionQuota;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.Profession;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.asworks.hrmobileapp_android.model.SessionManager;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {

    IApiService eventService = IApiService.retrofit.create(IApiService.class);
    private Employee currentUser;
    private Event currentEventDetail;
    private SessionManager currentSession;
    private List<EventProfessionQuota> professionQuotaList;
    AlertDialog eventAttendDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentSession = new SessionManager(getApplicationContext());
        currentUser = currentSession.GetCurrentUser();
        setContentView(R.layout.activity_eventdetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Call<ResponseBase<List<EventProfessionQuota>>> professionQuotaRequest = eventService.eventProfessionQuota(getIntent().getStringExtra("eventID"));

        professionQuotaRequest.enqueue(new Callback<ResponseBase<List<EventProfessionQuota>>>() {
            @Override
            public void onResponse(Call<ResponseBase<List<EventProfessionQuota>>> call, Response<ResponseBase<List<EventProfessionQuota>>> response) {
                professionQuotaList = response.body().data;
                TextView txtEventProfession = (TextView) findViewById(R.id.txtEventProfession);
                StringBuilder eventProfessionContent = new StringBuilder();
                if (professionQuotaList.size() > 0)
                {
                    eventProfessionContent.append("<span style='color:#51C4D4;font-size:16px'>Etkinlik Kotaları :</span><br/><br/>");

                    for (EventProfessionQuota professionItem : professionQuotaList) {
                        eventProfessionContent.append("<span style='color:#51C4D4'>Meslek Grubu : <span>" + professionItem.getProfession().getTitle() + "<br/>");
                        eventProfessionContent.append("<span style='color:#51C4D4'>Aranan Kişi Sayısı : </span>" + professionItem.getQuantity() + "<br/>");
                        eventProfessionContent.append("<span style='color:#51C4D4'>Cinsiyet : </span>" + professionItem.getGender() + "<br/>");
                        eventProfessionContent.append("<span style='color:#51C4D4'>Verilecek Ücret : </span>" + professionItem.getPrice() + " TL <br/>");
                        eventProfessionContent.append("<br/><br/>");
                    }
                }

                txtEventProfession.setText(Html.fromHtml(eventProfessionContent.toString()));
            }

            @Override
            public void onFailure(Call<ResponseBase<List<EventProfessionQuota>>> call, Throwable t) {

            }
        });

        Call<ResponseBase<Event>> eventRequest = eventService.eventDetail(getIntent().getStringExtra("eventID"));

        eventRequest.enqueue(new Callback<ResponseBase<Event>>() {
            @Override
            public void onResponse(Call<ResponseBase<Event>> call, Response<ResponseBase<Event>> response) {
                ResponseBase<Event> currentEvent = response.body();

                if (currentEvent.data != null) {
                    currentEventDetail = currentEvent.data;
                    getSupportActionBar().setTitle(currentEvent.data.getName());
                    ImageView imgThumb = (ImageView) findViewById(R.id.imgEventThumb);
                    TextView txtEventTitle = (TextView) findViewById(R.id.txtEventTitle);
                    TextView txtEventDate = (TextView) findViewById(R.id.txtEventDate);
                    TextView txtEventContent = (TextView) findViewById(R.id.txtEventContent);

                    Glide.with(getApplicationContext()).load(IApiService.apiUrl + currentEvent.data.getEventDocument()).into(imgThumb);
                    txtEventTitle.setText(currentEvent.data.getName());
                    txtEventDate.setText(currentEvent.data.getBeginDate() + " - " + currentEvent.data.getEndDate());

                    StringBuilder eventContent = new StringBuilder();
                    eventContent.append(currentEvent.data.getDescription());

                    if (currentEvent.data.getRestriction() != "")
                    {
                        eventContent.append("<br/><br/><span style='color:#51C4D4;font-size:16px'>Etkinlik Kuralları :</span><br/>");
                        eventContent.append(currentEvent.data.getRestriction());
                    }

                    txtEventContent.setText(Html.fromHtml(eventContent.toString()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBase<Event>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Hata Oluştu, Lütfen Tekrar Deneyiniz!", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton btnAttendEvent = (ImageButton)findViewById(R.id.btnAttendEvent);
        btnAttendEvent.setOnClickListener(btnAttendEventClick);
    }

    private View.OnClickListener btnCompleteAttendEventClick = new View.OnClickListener() {
        public void onClick(View v) {

            Spinner professionSelect = (Spinner) eventAttendDialog.findViewById(R.id.professionSelect);
            Integer selectedProfession = professionSelect.getSelectedItemPosition();
            List<Profession> currentUserProfessionList = currentSession.GetCurrentUser().getProfession();


            Call<ResponseBase<Event>> saveEventEmployeeRequest = eventService.saveEventEmployee(currentEventDetail.getID(), currentUser.getID(), currentUserProfessionList.get(selectedProfession).getID());

            saveEventEmployeeRequest.enqueue(new Callback<ResponseBase<Event>>() {
                @Override
                public void onResponse(Call<ResponseBase<Event>> call, Response<ResponseBase<Event>> response) {
                    if (response.body().success)
                    {
                        Toast.makeText(eventAttendDialog.getContext(), "Kayıt İşleminiz Tamamlandı." , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EventDetailActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(eventAttendDialog.getContext(), response.body().message , Toast.LENGTH_SHORT).show();
                        eventAttendDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBase<Event>> call, Throwable t) {
                    Toast.makeText(eventAttendDialog.getContext(), "Hata Oluştu, Lütfen Tekrar Deneyiniz!" , Toast.LENGTH_SHORT).show();
                    eventAttendDialog.dismiss();
                }
            });
        }};

    private View.OnClickListener btnAttendEventClick = new View.OnClickListener() {
        public void onClick(View v) {

            Employee currentUser = currentSession.GetCurrentUser();

            boolean userAvailabilityIsValid = currentUser.getEmployeeAvailability().size() > 0;
            boolean userJobExperienceIsValid = currentUser.getEmployeeJobExperience().size() > 0;
            boolean userEducationIsValid = currentUser.getEmployeeEducation().size() > 0;
            boolean userProfessionIsValid = currentUser.getProfession().size() > 0;

            if (userAvailabilityIsValid && userJobExperienceIsValid && userEducationIsValid && userProfessionIsValid) {

                View alertView = getLayoutInflater().inflate(R.layout.eventdetail_profession, null);

                EventAttendAdapter adapter = new EventAttendAdapter(EventDetailActivity.this, android.R.layout.simple_spinner_item, currentUser.getProfession());

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner sItems = (Spinner) alertView.findViewById(R.id.professionSelect);
                sItems.setAdapter(adapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailActivity.this);
                builder.setView(alertView);
                eventAttendDialog = builder.create();
                eventAttendDialog.show();
                ImageButton btnCompleteAttendEvent = (ImageButton)alertView.findViewById(R.id.btnCompleteEventAttend);
                btnCompleteAttendEvent.setOnClickListener(btnCompleteAttendEventClick);

            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailActivity.this);
                builder.setTitle(Html.fromHtml("<font color='#51C4D4'>Başvurunuzu yapmadan önce</font>"));

                StringBuilder message = new StringBuilder();
                message.append("Lütfen aşağıda listelenen profil bilgilerinizi <font color='#51C4D4'>eksiksiz</font> doldurunuz :<br /><br />");

                if (!userAvailabilityIsValid)
                {
                    message.append("<span style='color:#51C4D4;'>&#9724;</span><span style='color:#4C4C4C'>&nbsp;&nbsp;&nbsp;Çalışabileceğiniz gün & saatler</span>");
                }

                if (!userEducationIsValid)
                {
                    message.append("<span style='color:#51C4D4;'>&#9724;</span><span style='color:#4C4C4C'>&nbsp;&nbsp;&nbsp;Eğitim Bilgileriniz</span>");
                }

                if (!userJobExperienceIsValid)
                {
                    message.append("<span style='color:#51C4D4;'>&#9724;</span><span style='color:#4C4C4C'>&nbsp;&nbsp;&nbsp;İş Deneyimleriniz</span>");
                }

                if (!userProfessionIsValid)
                {
                    message.append("<span style='color:#51C4D4;'>&#9724;</span><span style='color:#4C4C4C'>&nbsp;&nbsp;&nbsp;Meslek Seçiminiz</span>");
                }

                builder.setMessage(Html.fromHtml(message.toString()));

                builder.setPositiveButton("PROFİLİME GİT",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "PROFİLİME GİT", Toast.LENGTH_SHORT).show();
                            }
                        });

                builder.setNegativeButton("GERİ DÖN",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(EventDetailActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
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