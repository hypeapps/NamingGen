package com.zaleski.rafal.nameshaker.features.main;

import com.zaleski.rafal.nameshaker.presenter.Presenter;

import javax.inject.Inject;

public class MainPresenter extends Presenter<MainView> {

    @Inject
    public MainPresenter() {
    }

    @Override
    protected void onAttachView(MainView view) {
        super.onAttachView(view);
        this.view.initPagerAdapter();
    }

    @Override
    protected void onDetachView() {
        super.onDetachView();
    }
}
