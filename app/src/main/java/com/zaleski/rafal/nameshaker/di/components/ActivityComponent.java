package com.zaleski.rafal.nameshaker.di.components;

import com.zaleski.rafal.nameshaker.di.PerActivity;
import com.zaleski.rafal.nameshaker.features.main.MainActivity;
import com.zaleski.rafal.nameshaker.features.sendinfo.SendInfoActivity;
import com.zaleski.rafal.nameshaker.features.sharefacebook.FacebookShareActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = {AppComponent.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(SendInfoActivity sendInfoActivity);

    void inject(FacebookShareActivity facebookShareActivity);
}
