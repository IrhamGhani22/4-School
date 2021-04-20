package com.application.a4_school.ui.help;

import android.os.Bundle;
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
import com.application.a4_school.RestAPI.ResponseData;
import com.application.a4_school.adapter.HelpViewAdapter;

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

        return root;
    }

    public void getdata(){
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseData> callHelp = api.gethelp();
        callHelp.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()){
                    try {
                        list.clear();
                        for (int i = 0; i < response.body().getHelp().size(); i++){
                            if (response.body().getHelp().get(i).getTitle().equals("title")){
                                list.add(response.body().getHelp().get(i));
                            }
                        }
                        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv.setAdapter(adapter);

//                        for (int i = 0; i<response.body())
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
}