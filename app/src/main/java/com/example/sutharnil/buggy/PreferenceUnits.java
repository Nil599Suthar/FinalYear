package com.example.sutharnil.buggy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUnits {

    public PreferenceUnits() {
    }

    public static void save(String name,String value ,Context context){

        SharedPreferences sharedPreferences= context.getSharedPreferences("login",context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(name,value);
        editor.apply();

    }
    public  static  String reademail(Context context,String name,String dvalue){
        SharedPreferences sharedPreferences= context.getSharedPreferences("login",context.MODE_PRIVATE);
        return sharedPreferences.getString(name,dvalue);

    }


}
