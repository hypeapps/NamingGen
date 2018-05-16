package com.zaleski.rafal.nameshaker.features.randomname;

import com.zaleski.rafal.nameshaker.model.NameFilter;
import com.zaleski.rafal.nameshaker.presenter.View;

public interface RandomNameView extends View {

    void openFilterDialog(NameFilter nameFilter);

    void invalidateFilterText(int which);

    void setRandomName(String name);

    void startCubeAnimation();

    void stopCubeAnimation();
}
