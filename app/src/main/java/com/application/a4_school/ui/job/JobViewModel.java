package com.application.a4_school.ui.job;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JobViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JobViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is job schedule fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}