package com.softmotions.apptodolist;

import android.app.Application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softmotions.ejdb2.EJDB2;
import com.softmotions.ejdb2.EJDB2Builder;

public class MyApplication extends Application {
    public static final String MSG_VAZIO = "Please complete this field!";

    public static EJDB2 ejdb2;
    public static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onCreate() {
        super.onCreate();
        String path = this.getApplicationInfo().dataDir + "/app.db";
        ejdb2 = new EJDB2Builder(path).open();
    }

    @Override
    public void onTerminate() {
        ejdb2.close();
        super.onTerminate();
    }
}
