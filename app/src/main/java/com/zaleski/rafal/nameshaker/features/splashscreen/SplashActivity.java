package com.zaleski.rafal.nameshaker.features.splashscreen;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.zaleski.rafal.nameshaker.R;
import com.zaleski.rafal.nameshaker.features.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements SplashView {

    private final SplashPresenter presenter = new SplashPresenter();

    private ValueAnimator animator = ValueAnimator.ofInt(0, 100);

    @BindView(R.id.splash_logo)
    public ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        presenter.onAttachView(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetachView();
        super.onDestroy();
    }

    @Override
    public void startSplash() {
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                if ((int) animation.getAnimatedValue() == 100) {
                    presenter.onSplashEnd();
                }
            }
        });

        animator.start();
    }

    @Override
    public void interruptSplash() {
        if (animator != null && animator.isStarted()) {
            animator.cancel();
        }
    }

    @Override
    public void startZoomInAnimation() {
        YoYo.with(Techniques.ZoomIn).playOn(logo);
    }

    @Override
    public void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
