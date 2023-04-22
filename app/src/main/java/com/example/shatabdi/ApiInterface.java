package com.example.shatabdi;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.sql.Date;

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

    @FormUrlEncoded
    @POST("u428418343_readarea.php")
    Call<GetAreaResponse> getAreaData(@Field("city") String city);

    @FormUrlEncoded
    @POST("u428418343_insertdealer.php")
    Call<InsertResponse> insertData(@Field("city") String city,@Field("area") String area,@Field("dealer") String dealer,@Field("dealer_name") String dealer_name,@Field("phone") String phone);

    @FormUrlEncoded
    @POST("u428418343_insertconversation.php")
    Call<InsertResponse> insertConversation(@Field("id") int id,@Field("salesman_name") String salesman_name,@Field("salesman_phone") String salesman_phone,@Field("conversation") String conversation,@Field("photo_loc") String photo_loc,@Field("lattitude") String lattitude,@Field("longitude") String longitude,@Field("location") String location,@Field("date") String date);

    @FormUrlEncoded
    @POST("u428418343_readsalesman.php")
    Call<GetSalesmanResponse> getSalesmanData(@Field("sdate") String sdate,@Field("tdate") String tdate);
    @FormUrlEncoded
    @POST("u428418343_getdealersvisited.php")
    Call<GetResponse2> readDealersVisited(@Field("salesman_name") String sname,@Field("sdate") String sdate,@Field("tdate") String tdate);
    @FormUrlEncoded
    @POST("u428418343_getconversation.php")
    Call<GetConversationResponse> readConversation(@Field("salesman_name") String sname,@Field("dealer") String dealer,@Field("date") String date);
    @FormUrlEncoded
    @POST("u428418343_getlocation.php")
    Call<GetLocationResponse> readLocation(@Field("dealer") String dealer,@Field("date") String date,@Field("conversation") String conversation);
    @FormUrlEncoded
    @POST("u428418343_getsalesmanlocation.php")
    Call<GetLocationResponse> readSalesmanLocation(@Field("salesman_name") String sname,@Field("date") String date,@Field("conversation") String conversation);
    @FormUrlEncoded
    @POST("u428418343_readattendance.php")
    Call<GetAttendanceResponse> readAttendance(@Field("sdate") String sdate,@Field("tdate") String tdate,@Field("salesman_name") String sname);
    @FormUrlEncoded
    @POST("u428418343_readdealerreport.php")
    Call<GetResponse2> readDealerReport(@Field("sdate") String sdate,@Field("tdate") String tdate);

    @FormUrlEncoded
    @POST("u428418343_getsalesmanvisited.php")
    Call<GetSalesmanVisitedResponse> readSalesmanVisited(@Field("dealer") String dealer,@Field("sdate") String sdate,@Field("tdate") String tdate);

    @FormUrlEncoded
    @POST("u428418343_getvisitedcity.php")
    Call<GetSalesmanVisitedResponse> readCityVisited(@Field("city") String city,@Field("area") String area,@Field("sdate") String sdate,@Field("tdate") String tdate);
    @FormUrlEncoded
    @POST("u428418343_getDealersinCity.php")
    Call<GetResponse2> readDealersInCity(@Field("salesman_name") String sname,@Field("city") String city,@Field("area") String area,@Field("date") String date);

}
