package com.zaleski.rafal.nameshaker.features.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zaleski.rafal.nameshaker.App;
import com.zaleski.rafal.nameshaker.R;
import com.zaleski.rafal.nameshaker.di.components.ActivityComponent;
import com.zaleski.rafal.nameshaker.di.components.DaggerActivityComponent;
import com.zaleski.rafal.nameshaker.features.sendinfo.SendInfoActivity;
import com.zaleski.rafal.nameshaker.features.sharefacebook.FacebookShareActivity;

import org.buffer.adaptablebottomnavigation.view.AdaptableBottomNavigationView;
import org.buffer.adaptablebottomnavigation.view.ViewSwapper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.main_view_swapper)
    public ViewSwapper viewSwapper;

    @BindView(R.id.main_bottom_navigation)
    public AdaptableBottomNavigationView bottomNavigation;

    private MainPagerAdapter pagerAdapter;

    @Inject
    public MainPresenter presenter;

    public ActivityComponent getComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(((App) getApplication()).getComponent())
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getComponent().inject(this);
        presenter.onAttachView(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetachView();
        super.onDestroy();
    }

    @Override
    public void initPagerAdapter() {
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewSwapper.setAdapter(pagerAdapter);
        bottomNavigation.setupWithViewSwapper(viewSwapper);
    }

    @Override
    @OnClick(R.id.fab_button_send_info)
    public void navigateToShareInfo() {
        Intent intent = new Intent(this, SendInfoActivity.class);
        startActivity(intent);
    }

    @Override
    @OnClick(R.id.fab_button_share_facebook)
    public void navigateToShare() {
        Intent intent = new Intent(this, FacebookShareActivity.class);
        startActivity(intent);
    }
}
