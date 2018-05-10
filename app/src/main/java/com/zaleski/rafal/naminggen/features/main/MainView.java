package com.zaleski.rafal.naminggen.features.main;

import com.zaleski.rafal.naminggen.presenter.View;

public interface MainView extends View {

    void initPagerAdapter();

    void navigateToShareInfo();

    void navigateToShare();
}
