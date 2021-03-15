package com.application.a4_school.UserInteraction;

import android.app.Dialog;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.a4_school.Models.Schedule;
import com.application.a4_school.Models.ScheduleData;
import com.application.a4_school.R;
import com.application.a4_school.adapter.ScheduleListAdapter;
import com.application.a4_school.ui.schedule.ScheduleFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class JobsBottomSheet extends BottomSheetDialogFragment {
    private AppBarLayout appBarLayout;
    private LinearLayout linearLayout;
    private RecyclerView rv_schedule;
    private TextView shTitle;
    ScheduleListAdapter adapter;
    String title;
    private ArrayList<Schedule> list = ScheduleFragment.getInstance().getList();

    public JobsBottomSheet(String days){
        this.title = days;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        final View view = View.inflate(getContext(), R.layout.fragment_jobs_bottom_sheet, null);
        dialog.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View)view.getParent());
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        appBarLayout    = view.findViewById(R.id.appbarBottomSheet);
        shTitle         = view.findViewById(R.id.textHeaderDays);
        linearLayout    = view.findViewById(R.id.bottom_sheet_linear);
        hideView(appBarLayout);
        rv_schedule     = view.findViewById(R.id.rv_schedule);
        Log.d("titleBottomSheet", "title: "+title);
        shTitle.setText(title);
        rv_schedule.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ScheduleListAdapter(list, getActivity());
        Log.d("BottomSheet", "listvalue: "+list);
        rv_schedule.setAdapter(adapter);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_EXPANDED == newState){
                    showView(appBarLayout, getActionBarSize());
                    hideView(linearLayout);
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == newState){
                    showView(linearLayout, getActionBarSize());
                    hideView(appBarLayout);
                }
                if (BottomSheetBehavior.STATE_HIDDEN == newState){
                    ScheduleFragment.getInstance().showRecyclerGrid();
                    dismiss();
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        view.findViewById(R.id.close_bottom_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleFragment.getInstance().showRecyclerGrid();
                dismiss();
            }
        });
        return dialog;
    }

    private void hideView(View view){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);
    }

    private void showView(View view, int size){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private int getActionBarSize(){
        final TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize
        });
        return (int) typedArray.getDimension(0,0);
    }
}