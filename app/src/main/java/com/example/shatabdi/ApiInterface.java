package com.example.shatabdi;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("u428418343_read.php")
    Call<GetResponse> getData();

    @GET("u428418343_readcity.php")
    Call<GetStringResponse> getCityData();

    @GET("u428418343_readarea.php")
    Call<GetAreaResponse> getAreaData();


}
