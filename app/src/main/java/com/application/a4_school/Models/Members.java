package com.application.a4_school.Models;

import com.google.gson.annotations.SerializedName;

public class Members {
    @SerializedName("id")
    private String id_user;
    private String name;
    private String email;
    private String nis;
    private String photo;

    public String getId_user() {
        return id_user;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNis() {
        return nis;
    }

    public String getPhoto() {
        return photo;
    }
}
