package com.application.a4_school.Models;

import java.util.ArrayList;

public class AttendanceData {
    public static int[] id_attendance = new int[]{
            1, 2, 3, 4, 5
    };

    public static String[] date = new String[]{
            "1 Jan",
            "2 Jan",
            "3 Jan",
            "4 Jan",
            "5 Jan"
    };

    public static String[] type = new String[]{
            "Theory",
            "Task",
            "Theory",
            "Task",
            "Task"
    };

    public static String[] tittle = new String[]{
            "Laporan Keuangan",
            "Dokumen Project PKK",
            "Marketing Plan dan Strategi",
            "Android Latihan Library",
            "Bussiness Plan"
    };

    public static int[] completedcount = new int[]{
            33, 34, 31, 29, 25
    };

    public static ArrayList<Attendance> getlistAttendance(){
        ArrayList<Attendance> list = new ArrayList<>();

        for (int i = 0; i<5; i++){
            Attendance attendance = new Attendance();
            attendance.setId_attendance(id_attendance[i]);
            attendance.setDate(date[i]);
            attendance.setType(type[i]);
            attendance.setTitle(tittle[i]);
            attendance.setCompletedcount(completedcount[i]);
            list.add(attendance);
        }
        return list;
    }
}
