package com.application.a4_school.ui.schedule;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.application.a4_school.Models.Schedule;
import com.application.a4_school.Models.ScheduleData;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.application.a4_school.RestAPI.ResponseData;
import com.application.a4_school.UserInteraction.JobsBottomSheet;
import com.application.a4_school.adapter.GridScheduleAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {
    RecyclerView rv_Schedule;
    private static ScheduleFragment instance;
    GridScheduleAdapter gridHeroAdapter;
    private ArrayList<Schedule> list = new ArrayList<>();
    int listSize;

    public static ScheduleFragment getInstance() {
        return instance;
    }

    public ArrayList<Schedule> getList() {
        return list;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        instance = this;
        rv_Schedule = root.findViewById(R.id.rv_schedule);
        rv_Schedule.setHasFixedSize(true);

        showRecyclerGrid();
        return root;
    }

    public void showRecyclerGrid(){
        if (!list.isEmpty()){
            list.clear();
        }
        list.addAll(ScheduleData.getListData());
        rv_Schedule.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        gridHeroAdapter = new GridScheduleAdapter(list, getActivity());
        gridHeroAdapter.notifyDataSetChanged();
        rv_Schedule.setAdapter(gridHeroAdapter);

        gridHeroAdapter.setOnItemClickCallback(new GridScheduleAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Schedule dataSchedule) {
                Toast.makeText(getActivity(), dataSchedule.getDays(), Toast.LENGTH_SHORT).show();
                final JobsBottomSheet jobsBottomSheet = new JobsBottomSheet(dataSchedule.getDays());
                jobsBottomSheet.show(getFragmentManager(), jobsBottomSheet.getTag());
            }
        });
    }

    public void getNowSchedule(){
        SharedPreferences getId_user = getActivity().getSharedPreferences("userInfo", 0);
        String id_user = getId_user.getString("id", "");
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseData> getScheduleNow = api.getListSchedule("1");
        getScheduleNow.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()){

                }else{

                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
}