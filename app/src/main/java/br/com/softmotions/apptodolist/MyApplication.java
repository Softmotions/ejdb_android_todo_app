package br.com.softmotions.apptodolist;

import android.app.Application;

import com.softmotions.ejdb2.EJDB2;
import com.softmotions.ejdb2.EJDB2Builder;
import io.realm.Realm;

import java.util.logging.Logger;

public class MyApplication extends Application {
    public static final String MSG_VAZIO = "Please complete this field!";

    public static Realm REALM;
    public static EJDB2 ejdb2;


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());
        REALM = Realm.getDefaultInstance();

        ejdb2 = new EJDB2Builder(this.getApplicationInfo().dataDir + "app.db").open();
    }

    @Override
    public void onTerminate() {
        ejdb2.close();
        super.onTerminate();
    }
}
