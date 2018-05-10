package com.zaleski.rafal.naminggen.features.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.zaleski.rafal.naminggen.features.namelist.NameListFragment;

import org.buffer.adaptablebottomnavigation.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {

    private static final int COUNT_PAGES = 1;

    private static final int FRAGMENT_NAME_LIST = 0;

    private static final int FRAGMENT_STATISTIC_DATA = 1;

    private static final int FRAGMENT_RANDOM_NAME = 2;

    public MainPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case FRAGMENT_NAME_LIST:
                return new NameListFragment();
            case FRAGMENT_RANDOM_NAME:
                break;
            case FRAGMENT_STATISTIC_DATA:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return COUNT_PAGES;
    }
}
