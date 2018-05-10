package com.zaleski.rafal.naminggen.features.main;

import com.zaleski.rafal.naminggen.presenter.Presenter;

public class MainPresenter extends Presenter<MainView> {

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
