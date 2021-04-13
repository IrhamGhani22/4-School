package com.application.a4_school.Models;

import com.google.gson.annotations.SerializedName;

public class ClassRoom {
    @SerializedName("id_kelas")
    private String id_classRoom;
    private String nama_matpel;
    @SerializedName("judul")
    private String title;
    @SerializedName("deskripsi")
    private String description;
    @SerializedName("tipe")
    private String type;
    @SerializedName("tenggat")
    private String deadline;
    @SerializedName("created_at")
    private String date;
    @SerializedName("file")
    private String file_url;
    @SerializedName("completed_count")
    private int completedcount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public int getCompletedcount() {
        return completedcount;
    }

    public void setCompletedcount(int completedcount) {
        this.completedcount = completedcount;
    }

    public String getId_classRoom() {
        return id_classRoom;
    }

    public void setId_classRoom(String id_classRoom) {
        this.id_classRoom = id_classRoom;
    }

    public String getNama_matpel() {
        return nama_matpel;
    }

    public void setNama_matpel(String nama_matpel) {
        this.nama_matpel = nama_matpel;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
