package com.application.a4_school.RestAPI;

import com.application.a4_school.Models.ClassRoom;
import com.application.a4_school.Models.Schedule;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {
    private String image_url;
    private List<Schedule> jadwal_mengajar;
    private List<ClassRoom> index_class_siswa;
    private List<ClassRoom> index_class_guru;
    @SerializedName("message")
    private String messageJson;

    public String getMessageJson() {
        return messageJson;
    }

    public String getImage_url() {
        return image_url;
    }

    public List<Schedule> getJadwal_mengajar() {
        return jadwal_mengajar;
    }

    public List<ClassRoom> getIndex_class_guru() {
        return index_class_guru;
    }

    public void setIndex_class_guru(List<ClassRoom> index_class_guru) {
        this.index_class_guru = index_class_guru;
    }
}
