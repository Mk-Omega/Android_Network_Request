package com.jpr.networkrequest.retrofitutil;

import com.jpr.networkrequest.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse> performUserSignIn(@Field("name") String name, @Field("username") String username, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<ApiResponse> performUserLogin(@Field("email") String email, @Field("password") String password);
}
