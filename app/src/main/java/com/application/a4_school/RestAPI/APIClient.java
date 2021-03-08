package com.application.a4_school.RestAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
<<<<<<< HEAD
    public static final String BASE_URL = "http://172.16.100.215:8000/api/";
=======
    public static final String BASE_URL = "http://192.168.43.234:8000/api/";
>>>>>>> 6492f538a53b15346d52972ad6b2380472864e64
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
