package com.application.a4_school.LocalStorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toolbar;

public class UserInfoStorage {
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private int mode = 0;
    private static final String APPNAME = "4-School";
    private static final String REFNAME = "userInfo";
    private static final String Id = "id";
    private static final String Name = "name";
    private static final String Email = "email";
    private static final String Image = "image";
    private Context context;

    public UserInfoStorage(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(REFNAME, mode);
        editor = sharedPreferences.edit();
    }
    public void createInfo(String name, String email, int id) {
        editor.putString(Name, name);
        editor.putString(Email, email);
        editor.putInt(Id, id);
        editor.commit();
    }
    public void addPict(String url){
        editor.putString(Image, url);
        editor.commit();
    }
    public void savename(String value){
        editor.putString(Name, value);
        editor.commit();
    }
    public void preferenceLogout(){
        editor.clear();
        editor.commit();
    }
    public void setPreference(Context context){
        sharedPreferences = context.getSharedPreferences(APPNAME, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

}
