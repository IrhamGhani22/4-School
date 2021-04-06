package com.application.a4_school.RestAPI;

import android.icu.number.Scale;

import com.application.a4_school.Models.Schedule;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseStudent {
    private String image;
    @SerializedName("message")
    private String messageJson;
    @SerializedName("jadwal_pelajaran")
    private ArrayList<Schedule> schedule;

    public String getMessageJson() {
        return messageJson;
    }

    public String getImage_student() {
        return image;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }
}
