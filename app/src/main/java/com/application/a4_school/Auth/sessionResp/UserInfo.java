package com.application.a4_school.Auth.sessionResp;

public class UserInfo {
    private int id;
    private String nip;
    private String nis;
    private String name;
    private String email;
    private String role;
    private String photo;
    private String fcm_token;

    public UserInfo() {
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
