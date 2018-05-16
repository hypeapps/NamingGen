package com.zaleski.rafal.nameshaker;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.zaleski.rafal.nameshaker.di.components.AppComponent;
import com.zaleski.rafal.nameshaker.di.components.DaggerAppComponent;
import com.zaleski.rafal.nameshaker.di.modules.AppModule;
import com.zaleski.rafal.nameshaker.di.modules.DatabaseModule;
import com.zaleski.rafal.nameshaker.util.DatabaseUtil;

import java.io.IOException;

public class App extends Application {

    private static final String DB_NAME = "names";

    public AppComponent getComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .databaseModule(new DatabaseModule(this, DB_NAME))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        try {
            DatabaseUtil.copyDataBase(this, DB_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
