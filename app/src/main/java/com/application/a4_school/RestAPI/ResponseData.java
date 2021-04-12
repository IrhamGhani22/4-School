package com.application.a4_school.RestAPI;

import com.application.a4_school.Models.ClassRoom;
import com.application.a4_school.Models.Members;
import com.application.a4_school.Models.Schedule;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {
    private String image_url;
    private int last_page;
    private List<Schedule> jadwal_mengajar;
    private List<ClassRoom> index_class_siswa;
    private List<ClassRoom> index_class_guru;
    private List<Members> members;

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

    public int getLast_page() {
        return last_page;
    }

    public List<ClassRoom> getIndex_class_siswa() {
        return index_class_siswa;
    }

    public List<Members> getMembers() {
        return members;
    }
}
