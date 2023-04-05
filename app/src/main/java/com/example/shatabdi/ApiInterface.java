package com.example.shatabdi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("read.php")
    Call<GetResponse> getData();


}
