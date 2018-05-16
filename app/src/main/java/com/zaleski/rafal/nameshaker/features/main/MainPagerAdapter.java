package com.zaleski.rafal.nameshaker.features.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.zaleski.rafal.nameshaker.features.namelist.NameListFragment;
import com.zaleski.rafal.nameshaker.features.randomname.RandomNameFragment;
import com.zaleski.rafal.nameshaker.features.statisticdata.StatisticDataFragment;

import org.buffer.adaptablebottomnavigation.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {

    private static final int COUNT_PAGES = 3;

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
            case FRAGMENT_STATISTIC_DATA:
                return new StatisticDataFragment();
            case FRAGMENT_RANDOM_NAME:
                return new RandomNameFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return COUNT_PAGES;
    }
}
