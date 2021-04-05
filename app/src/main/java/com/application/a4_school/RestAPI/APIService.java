package com.application.a4_school.RestAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

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

    @FormUrlEncoded
    @PATCH("upload/{id}")
    Call<ResponseBody> uploadBase64Pict(@Path("id") int id_user, @Field("photo") String encodedPhoto);

    @FormUrlEncoded
    @POST("SiswaSchedule")
    Call<ResponseStudent> getSiswaSchedule(@Field("id") int id_user, @Header("Authorization") String jwt_token);

    @FormUrlEncoded
    @POST("GuruSchedule")
    Call<ResponseData> getListSchedule(@Field("id") int id_user, @Header("Authorization") String jwt_token);

    @GET("classData")
    Call<JsonObject> getClassInformation (@Query("id_kelas") String id_class);

    @FormUrlEncoded
    @POST("GuruSchedule/upload-materi")
    Call<ResponseBody> uploadTaskTheory (@Field("id_kelas") String id_class,
                                         @Field("id_matpel") String id_matpel,
                                         @Field("judul") String title,
                                         @Field("deskripsi") String description,
                                         @Field("tipe") String type,
                                         @Field("tenggat") String deadline,
                                         @Header("Authorization") String jwt_token);
}
