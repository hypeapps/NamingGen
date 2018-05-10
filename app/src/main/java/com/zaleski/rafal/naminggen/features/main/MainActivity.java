package com.zaleski.rafal.naminggen.features.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zaleski.rafal.naminggen.R;

import org.buffer.adaptablebottomnavigation.view.ViewSwapper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.main_view_swapper)
    public ViewSwapper viewSwapper;

    private final MainPresenter presenter = new MainPresenter();

    private MainPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
    }

    @Override
    public void navigateToShareInfo() {

    }

    @Override
    public void navigateToShare() {

    }
}
