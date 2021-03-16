package com.application.a4_school.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.a4_school.Models.Home;
import com.application.a4_school.Models.HomeData;
import com.application.a4_school.Models.Schedule;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.adapter.HomeListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.application.a4_school.R.id.WebView1;
import static com.application.a4_school.R.id.content;
import static com.application.a4_school.R.id.rv_mapel;

public class HomeFragment extends Fragment {
    private HomeViewModel HomeViewModel;
    RecyclerView rv;
    private Context context;
    HomeListAdapter adapter;
    private ArrayList<Home> homeList = new ArrayList<>() ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
     View root = inflater.inflate(R.layout.fragment_home, container, false);

        rv = root.findViewById(rv_mapel);
        rv.setHasFixedSize(true);
        showList();

        return root;

    }

    public void showList(){
        homeList.addAll(HomeData.getlisthome());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomeListAdapter(homeList, getActivity());
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}