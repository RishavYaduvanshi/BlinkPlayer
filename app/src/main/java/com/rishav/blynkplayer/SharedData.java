package com.rishav.blynkplayer;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedData {
    private boolean bl;

    Context context;

    private SharedPreferences.Editor editor;

    private SharedPreferences sharedPreferences;

    public SharedData(Context paramContext) {
        this.context = paramContext;
        SharedPreferences sharedPreferences = paramContext.getSharedPreferences("settings", 0);
        this.sharedPreferences = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public boolean isBl() {
        boolean bool = this.sharedPreferences.getBoolean("Normal", false);
        this.bl = bool;
        return bool;
    }

    public void setBl(boolean paramBoolean) {
        this.bl = paramBoolean;
        this.editor.putBoolean("Normal", paramBoolean);
        this.editor.commit();
    }
}

