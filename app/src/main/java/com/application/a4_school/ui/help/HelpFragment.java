package com.application.a4_school.ui.help;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.a4_school.Models.Help;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.application.a4_school.adapter.HelpViewAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpFragment extends Fragment {
    private ArrayList<Help> list = new ArrayList<>();
    private RecyclerView rv;
    HelpViewAdapter adapter = new HelpViewAdapter(list);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help, container, false);
        rv  = root.findViewById(R.id.listhelp);
        getdata();

        return root;
    }

    public void getdata(){
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseBody> callHelp = api.gethelp();
        callHelp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Log.d("bisa ke api", "duh");
                        list.clear();
                        String responseJSON = response.body().string();
                        Gson objGson = new Gson();
                        Log.d("bisa ke api", responseJSON);
                        // TODO: 23/04/2021  rest api yang belum beres dan sangan menyulitkan
                        // TODO: 23/04/2021 masukin data ke list api
                        for(int i = 0 ; i < response.body().string().length(); i++){

                        }
                        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv.setAdapter(adapter);

//                        for (int i = 0; i<response.body())
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.d("ngga berhasil ", "kegagalaln");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("gagal", "gagal");
            }
        });
    }
}