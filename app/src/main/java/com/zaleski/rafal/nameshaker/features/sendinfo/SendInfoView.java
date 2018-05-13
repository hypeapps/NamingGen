package com.zaleski.rafal.nameshaker.features.sendinfo;

import com.zaleski.rafal.nameshaker.presenter.View;

public interface SendInfoView extends View {

    void initSpinners();

    void showSuccessfulSnackbar();

    void showError(String text);

}
