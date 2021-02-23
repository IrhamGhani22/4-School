package com.application.a4_school.LocalStorage;

import android.content.Context;
import android.content.SharedPreferences;

public class UserInfoStorage {
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private int mode = 0;
    private static final String REFNAME = "userInfo";
    private static final String Name = "name";
    private static final String Email = "email";
    private Context context;

    public UserInfoStorage(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(REFNAME, mode);
        editor = sharedPreferences.edit();
    }
    public void createSession(String name, String email) {
        editor.putString(Name, name);
        editor.putString(Email, email);
        editor.commit();
    }
}
