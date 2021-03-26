package com.application.a4_school.Models;

public class Attendance {
    private int id_attendance;
    private String date;
    private String type;
    private String title;
    private String description;
    private int completedcount;

    public int getId_attendance() {
        return id_attendance;
    }

    public void setId_attendance(int id_attendance) {
        this.id_attendance = id_attendance;
    }

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

    public int getCompletedcount() {
        return completedcount;
    }

    public void setCompletedcount(int completedcount) {
        this.completedcount = completedcount;
    }
}
