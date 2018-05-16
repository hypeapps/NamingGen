package com.zaleski.rafal.nameshaker.features.sharefacebook;

import android.graphics.Bitmap;

import com.zaleski.rafal.nameshaker.presenter.View;

interface FacebookShareView extends View {
    void setName(String name);

    void showError(String error);

    void onShare();

    void setPhoto(Bitmap photo);
}
