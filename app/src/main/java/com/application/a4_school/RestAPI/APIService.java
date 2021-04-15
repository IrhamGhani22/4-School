package com.application.a4_school.RestAPI;

import com.application.a4_school.Models.ClassRoom;
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

    @GET("SiswaSchedule")
    Call<ResponseStudent> getSiswaSchedule(@Query("id_kelas") String id_class, @Header("Authorization") String jwt_token);

    @GET("GuruSchedule")
    Call<ResponseData> getListSchedule(@Query("id") int id_user, @Header("Authorization") String jwt_token);

    @GET("classInfo")
    Call<JsonObject> getClassInformation (@Query("id_kelas") String id_class);

    @GET("SiswaSchedule/classRoomData")
    Call<ClassRoom> getClassData(@Query("id_kelas") String id_kelas, @Header("Authorization") String jwt_token);

    @FormUrlEncoded
    @POST("GuruSchedule/upload-materi")
    Call<ResponseBody> uploadTaskTheory (@Field("id_kelas") String id_class,
                                         @Field("id_matpel") String id_matpel,
                                         @Field("judul") String title,
                                         @Field("deskripsi") String description,
                                         @Field("tipe") String type,
                                         @Field("tenggat") String deadline,
                                         @Header("Authorization") String jwt_token);

    @FormUrlEncoded
    @POST("forgot")
    Call<JsonObject> sendMailToken (@Field("email") String email);

    @FormUrlEncoded
    @POST("resetpassword")
    Call<JsonObject> resetPassword(@Field("email") String email, @Field("password") String password, @Field("token") String accessToken);

    @GET("GuruSchedule/index_classroom_guru/{id_jadwal}")
    //inidirubah id_jadwal
    Call<ResponseData> getListClassItemGuru (@Path("id_jadwal") int id_jadwal, @Header("Authorization") String jwt_token);

    @GET("SiswaSchedule/index_classroom_siswa/{id_kelas}")
    Call<ResponseData> getListClassItemSiswa (@Path("id_kelas") String id_class, @Query("id_matpel") String id_matpel,@Header("Authorization") String jwt_token);

    @GET("index_classroom/memberclass")
    Call<ResponseData> getListMembersClass (@Query("id_kelas") String id_class, @Query("page") int page);

}
