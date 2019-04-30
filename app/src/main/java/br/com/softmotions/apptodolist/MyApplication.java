package br.com.softmotions.apptodolist;

import android.app.Application;

import io.realm.Realm;

public class MyApplication extends Application {
    public static final String MSG_VAZIO = "Please complete this field!";

    public static Realm REALM;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());
        REALM = Realm.getDefaultInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
