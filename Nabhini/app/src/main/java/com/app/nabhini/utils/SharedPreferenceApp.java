package com.app.nabhini.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceApp {
    SharedPreferences preferences;
    Editor editor =null;

    public SharedPreferenceApp(Context context) {
        this.preferences = context.getSharedPreferences("user",0);
        editor = preferences.edit();
    }

    public static SharedPreferenceApp getInstance(Context context) {
        return new SharedPreferenceApp(context);
    }

    public void saveNumber(String Key, int Value) {
        this.editor.putInt(Key, Value).apply();
    }


    public void clear() {
        this.editor.clear().apply();
    }


    public int getNumber(String Key, int defaultValue) {
        return this.preferences.getInt(Key, defaultValue);
    }

}
