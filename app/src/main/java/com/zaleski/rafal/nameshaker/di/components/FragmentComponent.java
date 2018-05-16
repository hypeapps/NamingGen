package com.zaleski.rafal.nameshaker.di.components;

import com.zaleski.rafal.nameshaker.di.PerFragment;
import com.zaleski.rafal.nameshaker.features.namelist.NameListFragment;
import com.zaleski.rafal.nameshaker.features.randomname.RandomNameFragment;
import com.zaleski.rafal.nameshaker.features.statisticdata.StatisticDataFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = {AppComponent.class})
public interface FragmentComponent {

    void inject(NameListFragment nameListFragment);

    void inject(StatisticDataFragment statisticDataFragment);

    void inject(RandomNameFragment randomNameFragment);
}
