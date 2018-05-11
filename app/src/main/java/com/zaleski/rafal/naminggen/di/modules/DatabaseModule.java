package com.zaleski.rafal.naminggen.di.modules;

import com.zaleski.rafal.naminggen.App;
import com.zaleski.rafal.naminggen.dataprovider.room.RoomService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private App app;

    private String databaseName;

    public DatabaseModule(App app, String databaseName) {
        this.app = app;
        this.databaseName = databaseName;
    }

    @Provides
    @Singleton
    public RoomService providesDatabase() {
        return RoomService.getAppDatabase(getApp(), databaseName);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public App getApp() {
        return app;
    }
}
