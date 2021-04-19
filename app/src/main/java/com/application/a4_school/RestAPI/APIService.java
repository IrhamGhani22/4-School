package com.application.a4_school.RestAPI;

import com.application.a4_school.Models.ClassRoom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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

    @GET("SiswaSchedule")
    Call<ResponseStudent> getSiswaSchedule(@Query("id_kelas") String id_class, @Header("Authorization") String jwt_token);

    @GET("GuruSchedule")
    Call<ResponseData> getListSchedule(@Query("id") int id_user, @Header("Authorization") String jwt_token);

    @GET("classInfo")
    Call<JsonObject> getClassInformation (@Query("id_kelas") String id_class);

    @GET("SiswaSchedule/classRoomData")
    Call<ClassRoom> getClassData(@Query("id_kelas") String id_kelas, @Header("Authorization") String jwt_token);

    @FormUrlEncoded
    @POST("forgot")
    Call<JsonObject> sendMailToken (@Field("email") String email);

    @Multipart
    @POST("GuruSchedule/create_tugas/{id_jadwal}")
    Call<ResponseBody> uploadTaskTheory (@Header("Authorization") String jwt_token,
                                       @Path("id_jadwal") int id_schedule,
                                       @Part("judul") RequestBody title,
                                       @Part("deskripsi") RequestBody description,
                                       @Part("tipe") RequestBody type,
                                       @Part("tenggat") RequestBody deadline,
                                       @Part MultipartBody.Part[] file);

    @FormUrlEncoded
    @POST("resetpassword")
    Call<JsonObject> resetPassword(@Field("email") String email, @Field("password") String password, @Field("token") String accessToken);

    @GET("GuruSchedule/index_classroom_guru/{id_jadwal}")
    //inidirubah id_jadwal
    Call<ResponseData> getListClassItemGuru (@Path("id_jadwal") int id_schedule, @Header("Authorization") String jwt_token);

    @GET("SiswaSchedule/index_classroom_siswa/{id_jadwal}")
    Call<ResponseData> getListClassItemSiswa (@Path("id_jadwal") int id_schedule, @Header("Authorization") String jwt_token);

    @GET("index_classroom/memberclass")
    Call<ResponseData> getListMembersClass (@Query("id_kelas") String id_class, @Query("page") int page);

    @GET("index_classroom/file/{id_tugas}")
    Call<ResponseData> getListFiles (@Path("id_tugas") String id_taskclass, @Query("condition") String condition);

    @GET("faq-content")
    Call<ResponseData> gethelp();

    @Streaming
    @GET()
    Call<ResponseBody> downloadFile(@Url String url);

}
