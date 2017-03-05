package com.asworks.hrmobileapp_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Event;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        IApiService eventService = IApiService.retrofit.create(IApiService.class);
        Call<ResponseBase<Event>> eventRequest = eventService.eventDetail(getIntent().getStringExtra("eventID"));

        eventRequest.enqueue(new Callback<ResponseBase<Event>>() {
            @Override
            public void onResponse(Call<ResponseBase<Event>> call, Response<ResponseBase<Event>> response) {
                ResponseBase<Event> currentEvent = response.body();

                if (currentEvent.data != null) {
                    getSupportActionBar().setTitle(currentEvent.data.getName());
                    ImageView imgThumb = (ImageView) findViewById(R.id.imgEventThumb);
                    TextView txtEventTitle = (TextView) findViewById(R.id.txtEventTitle);
                    TextView txtEventDate = (TextView) findViewById(R.id.txtEventDate);
                    TextView txtEventContent = (TextView) findViewById(R.id.txtEventContent);

                    Glide.with(getApplicationContext()).load("https://cms.aslabs.in/" + currentEvent.data.getEventDocument()).into(imgThumb);
                    txtEventTitle.setText(currentEvent.data.getName());
                    txtEventDate.setText(currentEvent.data.getBeginDate() + " - " + currentEvent.data.getEndDate());
                    txtEventContent.setText(Html.fromHtml(currentEvent.data.getDescription()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBase<Event>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Hata Oluştu, Lütfen Tekrar Deneyiniz!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}