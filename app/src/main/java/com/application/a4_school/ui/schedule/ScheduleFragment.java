package com.application.a4_school.ui.schedule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.application.a4_school.Models.Schedule;
import com.application.a4_school.Models.ScheduleData;
import com.application.a4_school.R;
import com.application.a4_school.UserInteraction.JobsBottomSheet;
import com.application.a4_school.adapter.GridScheduleAdapter;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment {
    RecyclerView rv_Schedule;
    private ArrayList<Schedule> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        rv_Schedule = root.findViewById(R.id.rv_schedule);
        rv_Schedule.setHasFixedSize(true);

        list.addAll(ScheduleData.getListData());
        showRecyclerGrid();
        return root;
    }

    private void showRecyclerGrid(){
        rv_Schedule.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        GridScheduleAdapter gridHeroAdapter = new GridScheduleAdapter(list, getActivity());
        rv_Schedule.setAdapter(gridHeroAdapter);

        gridHeroAdapter.setOnItemClickCallback(new GridScheduleAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Schedule dataSchedule) {
                Toast.makeText(getActivity(), dataSchedule.getDays(), Toast.LENGTH_SHORT).show();
                JobsBottomSheet jobsBottomSheet = new JobsBottomSheet();
                jobsBottomSheet.show(getFragmentManager(), jobsBottomSheet.getTag());

            }
        });
    }
}