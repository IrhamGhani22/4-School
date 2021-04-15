package com.application.a4_school.Auth.sessionResp;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    private int id;
    private String nip;
    private String nis;
    @SerializedName("id_kelas")
    private String id_class;
    private String name;
    private String email;
    private String role;
    private String photo;
    private String fcm_token;
    @SerializedName("profesi")
    private String profession;

    public UserInfo() {
    }

    public String getId_class() {
        return id_class;
    }

    public void setId_class(String id_class) {
        this.id_class = id_class;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getId() {
        return id;
    }

    public String getNip() {
        return nip;
    }

    public String getNis() {
        return nis;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getPhoto() {
        return photo;
    }

    public String getFcm_token() {
        return fcm_token;
    }
}
