package com.example.shatabdi;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("u428418343_readdealer.php")
    Call<GetResponse> getData(@Field("city") String city,@Field("area") String area);

    @GET("u428418343_readcity.php")
    Call<GetStringResponse> getCityData();

    @GET("u428418343_readarea.php")
    Call<GetAreaResponse> getAreaData();

    @FormUrlEncoded
    @POST("u428418343_insertdealer.php")
    Call<InsertResponse> insertData(@Field("city") String city,@Field("area") String area,@Field("dealer") String dealer,@Field("dealer_name") String dealer_name,@Field("phone") String phone);

    @FormUrlEncoded
    @POST("u428418343_insertconversation.php")
    Call<InsertResponse> insertConversation(@Field("id") int id,@Field("salesman_name") String salesman_name,@Field("conversation") String conversation,@Field("photo_loc") String photo_loc,@Field("lattitude") String lattitude,@Field("longitude") String longitude,@Field("date") String date);

}
