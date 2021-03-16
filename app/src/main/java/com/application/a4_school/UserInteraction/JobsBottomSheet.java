package com.application.a4_school.UserInteraction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.a4_school.Auth.Login;
import com.application.a4_school.Models.Schedule;
import com.application.a4_school.Models.ScheduleData;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.application.a4_school.RestAPI.ResponseData;
import com.application.a4_school.adapter.ScheduleListAdapter;
import com.application.a4_school.ui.schedule.ScheduleFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobsBottomSheet extends BottomSheetDialogFragment {
    private AppBarLayout appBarLayout;
    private LinearLayout linearLayout;
    private RecyclerView rv_schedule;
    private TextView shTitle, shMessage;
    private ProgressBar progressBar;
    private Button btnRefresh;
    private Context context;
    ScheduleListAdapter adapter;
    String title;
    boolean isSuccess;
    private ArrayList<Schedule> list = new ArrayList<>();

    public JobsBottomSheet(String days){
        this.title = days;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
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
        shMessage       = view.findViewById(R.id.txtMessageBtmSheet);
        linearLayout    = view.findViewById(R.id.bottom_sheet_linear);
        rv_schedule     = view.findViewById(R.id.rv_schedule);
        progressBar     = view.findViewById(R.id.bottom_loading);
        btnRefresh      = view.findViewById(R.id.btn_refresh);
        hideView(appBarLayout);
        getListScheduleData();
        Log.d("titleBottomSheet", "title: "+title);
        shTitle.setText(title);

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

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                getListScheduleData();
            }
        });
        return dialog;
    }

    private void getListScheduleData(){
        SharedPreferences getId_user = getActivity().getSharedPreferences("userInfo", 0);
        String id_user = getId_user.getString("id", "");
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseData> listSchedule = api.getListSchedule("1");
        listSchedule.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, final Response<ResponseData> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        try {
                            list.clear();
                            list.addAll(response.body().getJadwal_mengajar());
                            if (list.isEmpty()){
                                shMessage.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                shMessage.setText("You dont have schedule on this day");
                            }else{
                                rv_schedule.setVisibility(View.VISIBLE);
                                shMessage.setVisibility(View.GONE);
                                btnRefresh.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                rv_schedule.setLayoutManager(new LinearLayoutManager(getActivity()));
                                adapter = new ScheduleListAdapter(list, getActivity());
                                rv_schedule.setAdapter(adapter);
                                Log.d("sendparameter", "isSuccess : true");
                                Log.d("ScheduleFragment", "Success: "+response.body().getJadwal_mengajar());
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if (response.code() == 401){
                        startActivity(new Intent(context, Login.class));
                        Toast.makeText( context, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.code() == 422){
                        Toast.makeText( context, "Terjadi Kesalahan silakan refresh terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }else if (response.code() == 403){
                        Toast.makeText(context, "Unauthorized", Toast.LENGTH_SHORT).show();
                    }else if (response.code() == 404){
                        Toast.makeText(context, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show();
                    }else if (response.code() == 405){
                        Toast.makeText(context, "Method Tidak diterima server, silakan login kembali", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, Login.class));

                    }
                }else{
                    Log.d("ScheduleFragment", "System error");
                    progressBar.setVisibility(View.GONE);
                    btnRefresh.setVisibility(View.VISIBLE);
                    shMessage.setVisibility(View.VISIBLE);
                    shMessage.setText("Unknown System error");
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnRefresh.setVisibility(View.VISIBLE);
                shMessage.setVisibility(View.VISIBLE);
                shMessage.setText("Can't connect to server, please check your internet connection");
                Log.d("ScheduleFragment", "System error : "+t.getMessage());
            }
        });
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