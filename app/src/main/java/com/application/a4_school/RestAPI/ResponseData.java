package com.application.a4_school.RestAPI;

import com.application.a4_school.Auth.sessionResp.UserInfo;
import com.application.a4_school.Models.ClassRoom;
import com.application.a4_school.Models.FilesUpload;
import com.application.a4_school.Models.Help;
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
    @SerializedName("kelas")
    private List<UserInfo> listMajors;
    private UserInfo information;
    private List<FilesUpload> filesDetail;
    private List<Help> help;

    public UserInfo getInformation() {
        return information;
    }

    public List<UserInfo> getListMajors() {
        return listMajors;
    }

    public void setListMajors(List<UserInfo> listMajors) {
        this.listMajors = listMajors;
    }

    public List<Help> getHelp() {
        return help;
    }

    public void setHelp(List<Help> help) {
        this.help = help;
    }

    @SerializedName("message")
    private String messageJson;

    public List<FilesUpload> getFilesDetail() {
        return filesDetail;
    }

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
