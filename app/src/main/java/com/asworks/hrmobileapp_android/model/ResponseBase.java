package com.asworks.hrmobileapp_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBase<T> {

    @SerializedName("Success")
    @Expose
    public boolean success;

    @SerializedName("Message")
    @Expose
    public String message;

    @SerializedName("Data")
    @Expose
    public T data;
}