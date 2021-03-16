package com.application.a4_school.RestAPI;

import com.application.a4_school.Models.Schedule;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {
    private String image_url;
    private List<Schedule> jadwal_mengajar;

    public String getImage_url() {
        return image_url;
    }

    public List<Schedule> getJadwal_mengajar() {
        return jadwal_mengajar;
    }
}
