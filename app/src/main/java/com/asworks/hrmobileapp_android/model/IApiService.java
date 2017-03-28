package com.asworks.hrmobileapp_android.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IApiService {

    public static final String apiUrl = "https://cms.aslabs.in/";

    public static final Retrofit retrofit =
             new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/api/aday/giris")
    public Call<ResponseBase<Employee>> login(@Query("mail") String mail, @Query("password") String password);

    @POST("/api/aday/yeni-aday")
    public Call<ResponseBase<Employee>> register(@Body Employee employeeEntity);

    @GET("api/etkinlik/etkinlik-listesi")
    public  Call<ResponseBase<List<Event>>> eventList();

    @GET("api/etkinlik/gecmis-etkinlik-listesi")
    public  Call<ResponseBase<List<Event>>> pastEventList();

    @GET("api/etkinlik/katildigin-etkinlikler/{employeeID}")
    public  Call<ResponseBase<List<Event>>> attendedEventList(@Query("employeeID") String employeeID);

    @GET("api/etkinlik/etkinlik/{eventID}")
    public  Call<ResponseBase<Event>> eventDetail(@Query("eventID") String eventID);

    @GET("api/etkinlik-kota/etkinlik-kota/{eventID}")
    public  Call<ResponseBase<List<EventProfessionQuota>>> eventProfessionQuota(@Query("eventID") String eventID);

    @POST("api/etkinlik/etkinlik-aday-kayit")
    public Call<ResponseBase<Event>> saveEventEmployee(@Query("eventID") Integer eventID, @Query("employeeID") Integer employeeID, @Query("professionID") Integer professionID);

}