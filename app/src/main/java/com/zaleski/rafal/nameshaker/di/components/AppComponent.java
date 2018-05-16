package com.zaleski.rafal.nameshaker.di.components;

import android.app.Application;

import com.zaleski.rafal.nameshaker.App;
import com.zaleski.rafal.nameshaker.dataprovider.datasource.NameDataRepository;
import com.zaleski.rafal.nameshaker.di.modules.AppModule;
import com.zaleski.rafal.nameshaker.di.modules.DatabaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class})
public interface AppComponent {

    void inject(App app);

    Application application();

    NameDataRepository nameDataRepository();

}
