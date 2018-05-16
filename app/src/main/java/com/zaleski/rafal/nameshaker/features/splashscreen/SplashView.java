package com.zaleski.rafal.nameshaker.features.splashscreen;

import com.zaleski.rafal.nameshaker.presenter.View;

public interface SplashView extends View {

    void startZoomInAnimation();

    void startSplash();

    void interruptSplash();

    void navigateToMainActivity();
}
