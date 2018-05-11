package com.zaleski.rafal.naminggen.di.components;

import com.zaleski.rafal.naminggen.di.PerFragment;
import com.zaleski.rafal.naminggen.features.namelist.NameListFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = {AppComponent.class})
public interface FragmentComponent {

    void inject(NameListFragment nameListFragment);
}
