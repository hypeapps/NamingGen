package com.zaleski.rafal.nameshaker.features.statisticdata;

import android.arch.paging.PagedList;

import com.zaleski.rafal.nameshaker.model.Name;
import com.zaleski.rafal.nameshaker.model.NameFilter;
import com.zaleski.rafal.nameshaker.presenter.View;

interface StatisticDataView extends View {
    void initRecyclerAdapter();

    void onNextItemList(PagedList<Name> pagedList);

    void clearListRecycler();

    void openFilterGenderDialog(NameFilter nameFilter);

    void invalidateFilterText(int whichGender, String voivodeship);
}
