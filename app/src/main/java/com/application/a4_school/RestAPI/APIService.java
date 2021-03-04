package com.application.a4_school.RestAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded",
    })
    @POST("login")
    Call<ResponseBody> login(@Field("email") String email,
                                @Field("password") String password);
                                //@Field("fcm_token") String fcm_token);

    @GET("logout/{id}")
    Call<ResponseBody> logoutUser(@Path("id") String id_user,
                                  @Query("token") String logoutToken);
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> register(@Field("email") String email,
                                @Field("password") String pw,
                                @Field("name") String name);

    @PATCH("/uploadPict/{id}")
    Call<ResponseBody> uploadBase64Pict(@Path("id") String id_user, @Field("photo") String encodedPhoto);
}
