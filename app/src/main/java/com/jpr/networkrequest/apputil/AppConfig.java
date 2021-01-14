package com.jpr.networkrequest.apputil;

import android.content.Context;
import android.content.SharedPreferences;

import com.jpr.networkrequest.R;

public class AppConfig {

    private Context context;
    private SharedPreferences sharedPreferences;

    public AppConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file_key), context.MODE_PRIVATE);
    }

    public boolean isUserLogin(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_is_user_login), false);
    }

    public void updateUserLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_user_login), status);
        editor.apply();
    }

    public void saveUserInfo(String name, String username){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_name_of_user), name);
        editor.putString(context.getString(R.string.pref_username_of_user), username);
        editor.apply();
    }

    public String getNameInfo(){
        return sharedPreferences.getString(context.getString(R.string.pref_name_of_user), "Unknown");
    }

    public String getUsernameInfo(){
        return sharedPreferences.getString(context.getString(R.string.pref_username_of_user), "Unknown");
    }

}
