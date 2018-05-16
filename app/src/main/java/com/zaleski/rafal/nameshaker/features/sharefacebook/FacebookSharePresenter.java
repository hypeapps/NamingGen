package com.zaleski.rafal.nameshaker.features.sharefacebook;

import android.graphics.Bitmap;

import com.zaleski.rafal.nameshaker.presenter.Presenter;

import javax.inject.Inject;

public class FacebookSharePresenter extends Presenter<FacebookShareView> {

    @Inject
    public FacebookSharePresenter() {
    }

    @Override
    protected void onAttachView(FacebookShareView view) {
        super.onAttachView(view);
    }

    @Override
    protected void onDetachView() {
        super.onDetachView();
    }

    public void onTextChanged(CharSequence s) {
        this.view.setName(s.toString());
    }

    public void takingPhotoSuccess(Bitmap scaledPhoto) {
        this.view.setPhoto(scaledPhoto);
    }
}
