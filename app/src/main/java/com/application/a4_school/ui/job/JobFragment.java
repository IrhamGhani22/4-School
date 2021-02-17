package com.application.a4_school.ui.job;

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

import com.application.a4_school.R;

public class JobFragment extends Fragment {

    private JobViewModel jobViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        jobViewModel =
                ViewModelProviders.of(this).get(JobViewModel.class);
        View root = inflater.inflate(R.layout.fragment_job, container, false);
//        final TextView textView = root.findViewById(R.id.text_job);
        jobViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }
}