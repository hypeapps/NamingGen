package com.zaleski.rafal.nameshaker.features.main;

import com.zaleski.rafal.nameshaker.presenter.View;

public interface MainView extends View {

    void initPagerAdapter();

    void navigateToShareInfo();

    void navigateToShare();
}
