package com.zaleski.rafal.naminggen.features.statisticdata;

import android.arch.paging.PagedList;

import com.zaleski.rafal.naminggen.dataprovider.datasource.NameFilter;
import com.zaleski.rafal.naminggen.model.Name;
import com.zaleski.rafal.naminggen.presenter.View;

interface StatisticDataView extends View {
    void initRecyclerAdapter();

    void onNextItemList(PagedList<Name> pagedList);

    void clearListRecycler();

    void openFilterGenderDialog(NameFilter nameFilter);

    void invalidateFilterText(int whichGender, String voivodeship);
}
