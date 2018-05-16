package com.zaleski.rafal.nameshaker.di.modules;

import android.app.Application;

import com.zaleski.rafal.nameshaker.dataprovider.datasource.NameDataRepository;
import com.zaleski.rafal.nameshaker.dataprovider.datasource.NameDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    NameDataRepository nameDataRepository(NameDataSource nameDataRepository) {
        return nameDataRepository;
    }

}
