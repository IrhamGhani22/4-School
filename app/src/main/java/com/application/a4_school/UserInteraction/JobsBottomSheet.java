package com.application.a4_school.UserInteraction;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.application.a4_school.RestAPI.ResponseStudent;
import com.application.a4_school.ui.classroom.ClassRoomActivity;
import com.application.a4_school.Auth.Login;
import com.application.a4_school.Auth.SessionManager;
import com.application.a4_school.LocalStorage.UserInfoStorage;
import com.application.a4_school.Models.Schedule;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.application.a4_school.RestAPI.ResponseData;
import com.application.a4_school.adapter.GridScheduleAdapter;
import com.application.a4_school.adapter.ScheduleListAdapter;
import com.application.a4_school.ui.schedule.ScheduleFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobsBottomSheet extends BottomSheetDialogFragment {
    private AppBarLayout appBarLayout;
    private LinearLayout linearLayout;
    private RecyclerView rv_schedule;
    private TextView shTitle, shMessage;
    private LottieAnimationView progressBar;
    private Button btnRefresh;
    private UserInfoStorage userInfoStorage;
    private SessionManager sessionManager;
    private String role;
    ScheduleListAdapter adapter;
    String title;
    boolean isSuccess;
    private ArrayList<Schedule> list = new ArrayList<>();

    public JobsBottomSheet(String days, String role) {
        this.title = days;
        this.role = role;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        userInfoStorage = new UserInfoStorage(getActivity().getApplicationContext());
        sessionManager = new SessionManager(getActivity().getApplicationContext());
//        userInfoStorage.setPreference(context);
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
        final View mView = View.inflate(getContext(), R.layout.fragment_jobs_bottom_sheet, null);
        dialog.setContentView(mView);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) mView.getParent());
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        //appBarLayout = mView.findViewById(R.id.appbarBottomSheet);
        shTitle = mView.findViewById(R.id.textHeaderDays);
        shMessage = mView.findViewById(R.id.txtMessageBtmSheet);
        linearLayout = mView.findViewById(R.id.bottom_sheet_linear);
        rv_schedule = mView.findViewById(R.id.rv_schedule);
        progressBar = mView.findViewById(R.id.bottom_loading);
        btnRefresh = mView.findViewById(R.id.btn_refresh);
        //hideView(appBarLayout);

        //Role
        if (role.equals("guru")){
            getListScheduleGuruData();
        }else{
            getListScheduleSiswa();
        }
        Log.d("titleBottomSheet", "title: " + title);
        shTitle.setText(title);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
//                    int height = bottomSheet.getHeight();
//                    showView(appBarLayout, getActionBarSize());
//                    hideView(linearLayout);
//                }
//                if (BottomSheetBehavior.STATE_DRAGGING == newState){
//
//                }
//                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
//                    showView(linearLayout, getActionBarSize());
//                    hideView(appBarLayout);
//                }
                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    ScheduleFragment.getInstance().showRecyclerGrid();
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        mView.findViewById(R.id.close_bottom_sheet).setOnClickListener(new View.OnClickListener() {
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
                if (role.equals("guru")){
                    getListScheduleGuruData();
                }else{
                    getListScheduleSiswa();
                }
            }
        });
        return dialog;
    }

    private void getListScheduleGuruData() {
        SharedPreferences getId_user = getActivity().getSharedPreferences("userInfo", 0);
        int id_user = getId_user.getInt("id", 0);
        String token = getActivity().getSharedPreferences("session", 0).getString("token", "");
        Log.d("tokenvalue", "value: " + token);
        Log.d("tokenvalue", "value: " + id_user);
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseData> listSchedule = api.getListSchedule(id_user, "Bearer " + token);
        listSchedule.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, final Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    try {
                        list.clear();
                        for (int i = 0; i < response.body().getJadwal_mengajar().size(); i++) {
                            if (response.body().getJadwal_mengajar().get(i).getDays().equals(title)) {
                                list.add(response.body().getJadwal_mengajar().get(i));
                            }
                        }
                        if (response.body().getJadwal_mengajar().isEmpty()) {
                            shMessage.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            shMessage.setText("You dont have schedule on this day");
                        } else {
                            rv_schedule.setVisibility(View.VISIBLE);
                            shMessage.setVisibility(View.GONE);
                            btnRefresh.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            rv_schedule.setLayoutManager(new LinearLayoutManager(getActivity()));
                            adapter = new ScheduleListAdapter(list, getActivity());
                            rv_schedule.setAdapter(adapter);
                            adapter.setOnItemClickCallback(new GridScheduleAdapter.OnItemClickCallback() {
                                @Override
                                public void onItemClicked(Schedule dataSchedule) {
                                    Intent toAttendance = new Intent(getActivity(), ClassRoomActivity.class);
                                    toAttendance.putExtra("EXTRA_CLASS", dataSchedule.getId_kelas());
                                    dismiss();
                                    startActivity(toAttendance);
                                }
                            });
                            Log.d("sendparameter", "isSuccess : true");
                            Log.d("ScheduleFragment", "Success: " + response.body().getJadwal_mengajar());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        btnRefresh.setVisibility(View.VISIBLE);
                        shMessage.setVisibility(View.VISIBLE);
                        shMessage.setText("Unknown error");
                    }
                } else {
                    switch (response.code()){
                        case 401:
                            progressBar.setVisibility(View.GONE);
                            btnRefresh.setVisibility(View.VISIBLE);
                            btnRefresh.setText("Relogin here");
                            shMessage.setVisibility(View.VISIBLE);
                            shMessage.setText("The session has ended, please login again");
                            btnRefresh.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    userInfoStorage.clearUser();
                                    sessionManager.preferenceLogout();
                                    startActivity(new Intent(getActivity(), Login.class));
                                    getActivity().finishAffinity();
                                }
                            });
                            break;

                        case 422:
                            progressBar.setVisibility(View.GONE);
                            btnRefresh.setVisibility(View.VISIBLE);
                            shMessage.setVisibility(View.VISIBLE);
                            shMessage.setText("An error occurs, please refresh first");
                            break;

                        default:
                            progressBar.setVisibility(View.GONE);
                            btnRefresh.setVisibility(View.VISIBLE);
                            shMessage.setVisibility(View.VISIBLE);
                            shMessage.setText("Unknown error");
                            break;
                    }
                }
            }


            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnRefresh.setVisibility(View.VISIBLE);
                shMessage.setVisibility(View.VISIBLE);
                shMessage.setText("Can't connect to server, please check your internet connection");
                Log.d("ScheduleFragment", "System error : " + t.getMessage());
            }
        });
    }

    private void getListScheduleSiswa(){
        SharedPreferences getId_class = getActivity().getSharedPreferences("userInfo", 0);
        String id_class = getId_class.getString("id_class", "");
        String token = getActivity().getSharedPreferences("session", 0).getString("token", "");
        Log.d("tokenvalue", "value: " + token);
        Log.d("tokenvalue", "value: " + id_class);
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseStudent> StudentSchedule = api.getSiswaSchedule(id_class, "Bearer " + token);
        StudentSchedule.enqueue(new Callback<ResponseStudent>() {
            @Override
            public void onResponse(Call<ResponseStudent> call, Response<ResponseStudent> response) {
                if(response.isSuccessful()){
                    if (response.body() != null){
                        try {
                            for (int i = 0; i < response.body().getSchedule().size(); i++){
                                if (response.body().getSchedule().get(i).getDays().equals(title)) {
                                    list.add(response.body().getSchedule().get(i));
                                }
                            }
                            rv_schedule.setVisibility(View.VISIBLE);
                            shMessage.setVisibility(View.GONE);
                            btnRefresh.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            rv_schedule.setLayoutManager(new LinearLayoutManager(getActivity()));
                            adapter = new ScheduleListAdapter(list, getActivity());
                            rv_schedule.setAdapter(adapter);
                            Log.d("sendparameter", "isSuccess : true");
                            Log.d("ScheduleFragment", "Success: " + response.body().getSchedule());
                        }catch (Exception e){
                            e.printStackTrace();
                            shMessage.setVisibility(View.VISIBLE);
                            shMessage.setText("Can't connect to server, please check your internet connection");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseStudent> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnRefresh.setVisibility(View.VISIBLE);
                shMessage.setVisibility(View.VISIBLE);
                shMessage.setText("Can't connect to server, please check your internet connection");
                Log.d("ScheduleFragment", "System error : " + t.getMessage());
            }
        });



    }

    private void hideView(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);
    }

    private void showView(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private int getActionBarSize() {
        final TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize
        });
        return (int) typedArray.getDimension(0, 0);
    }
}