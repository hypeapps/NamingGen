package com.zaleski.rafal.nameshaker.features.splashscreen;

import com.zaleski.rafal.nameshaker.presenter.Presenter;

public class SplashPresenter extends Presenter<SplashView> {

    @Override
    protected void onAttachView(SplashView view) {
        super.onAttachView(view);
        this.view.startSplash();
        this.view.startZoomInAnimation();
    }

    @Override
    protected void onDetachView() {
        super.onDetachView();
        if (this.view != null) this.view.interruptSplash();
    }

    public void onSplashEnd() {
        this.view.navigateToMainActivity();
    }
}
