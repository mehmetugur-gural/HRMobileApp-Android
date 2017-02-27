package com.asworks.hrmobileapp_android.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiService {

    public static final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://cms.aslabs.in/")
            .addConverterFactory(GsonConverterFactory.create()).build();

    @GET("/api/aday/giris")
    public Call<ResponseBase<Employee>> login(@Query("mail") String mail, @Query("password") String password);

    @GET("api/etkinlik/etkinlik-listesi")
    public  Call<ResponseBase<List<Event>>> eventList();
}
