package com.zaleski.rafal.naminggen.di.components;

import com.zaleski.rafal.naminggen.di.PerActivity;
import com.zaleski.rafal.naminggen.features.main.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = {AppComponent.class})
public interface ActivityComponent {

    void inject(MainActivity activity);
}
