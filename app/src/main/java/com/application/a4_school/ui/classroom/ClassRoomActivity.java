package com.application.a4_school.ui.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.application.a4_school.Models.ClassRoom;
import com.application.a4_school.Models.ClassRoomData;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.application.a4_school.RestAPI.ResponseData;
import com.application.a4_school.adapter.ClassListAdapter;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassRoomActivity extends AppCompatActivity {
    private RecyclerView rvClassroom;
    private ClassListAdapter adapter;
    private ArrayList<ClassRoom> list = new ArrayList<>();
    private LottieAnimationView loading_classroom;
    private static String[] headerClassContent;
    TextView btnInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_room);
        initialize();
        rvClassroom.setLayoutManager(new LinearLayoutManager(this));
        String id_class = getIntent().getStringExtra("EXTRA_CLASS");
        Log.d("infoClass", ""+id_class);
        getInfoClass(id_class);
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClassRoomActivity.this, FormClassRoomActivity.class));
            }
        });
    }

    public void initialize(){
        rvClassroom = findViewById(R.id.rv_class);
        btnInput = findViewById(R.id.addInputFormClass);
        loading_classroom = findViewById(R.id.loading_classroom);
    }

    public void getInfoClass(final String id_class){
        final String token = getSharedPreferences("session", 0).getString("token", "");
        final APIService api = APIClient.getClient().create(APIService.class);
        Call<JsonObject> loadClassInformation= api.getClassInformation(id_class);
        loadClassInformation.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    JsonObject object = response.body().getAsJsonObject("class_info");
                    headerClassContent = new String[]{
                            object.get("id").toString().replaceAll("\"", ""),
                            object.get("tingkatan").toString().replaceAll("\"", ""),
                            object.get("jurusan").toString().replaceAll("\"", ""),
                            object.get("class_member").toString().replaceAll("\"", "")
                    };
                    getItemClass(api, id_class, token, headerClassContent);

                }else{
                    if (response.body().getAsJsonObject("class_info") != null){
                        Log.d("classinfo", ""+response.body().getAsJsonObject("class_info"));
                    }else{
                        Log.d("classinfo", "Object null");
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("classinfo", ""+t.getMessage());
            }
        });
    }

    public void getItemClass(APIService api, final String id_class, String token, final String[] headerClassContent){
        String role = getSharedPreferences("session", 0).getString("role", "");
        Call<ResponseData> loadClassRoomGuru = api.getListClassItemGuru(id_class, "Bearer "+token);
        Call<ResponseData> loadClassRoomSiswa = api.getListClassItemSiswa(id_class, "Bearer "+token);
        if(role.equals("guru")){
            loadClassRoomGuru.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if (response.isSuccessful()){
                        list.addAll(ClassRoomData.getlistClassroom());
                        list.addAll(response.body().getIndex_class_guru());
                        adapter = new ClassListAdapter(list, headerClassContent, id_class,ClassRoomActivity.this);
                        rvClassroom.setAdapter(adapter);
                        loading_classroom.setVisibility(View.GONE);
                        Log.d("getClassData", "success : "+response.body().getIndex_class_guru());
                    }else{
                        Log.d("getClassData", "response not success");
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Log.d("getClassData", "response not success"+t.getMessage());
                }
            });
        }else{
            loadClassRoomSiswa.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    list.addAll(ClassRoomData.getlistClassroom());
                    list.addAll(response.body().getIndex_class_siswa());
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {

                }
            });
        }

    }

}