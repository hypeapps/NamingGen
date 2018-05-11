package com.zaleski.rafal.naminggen.di.components;

import android.app.Application;

import com.zaleski.rafal.naminggen.App;
import com.zaleski.rafal.naminggen.dataprovider.datasource.NameDataRepository;
import com.zaleski.rafal.naminggen.di.modules.AppModule;
import com.zaleski.rafal.naminggen.di.modules.DatabaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class})
public interface AppComponent {

    void inject(App app);

    Application application();

    NameDataRepository nameDataRepository();

}
