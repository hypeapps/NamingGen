package com.zaleski.rafal.naminggen.features.namelist;

import android.arch.paging.PagedList;

import com.zaleski.rafal.naminggen.dataprovider.datasource.NameFilter;
import com.zaleski.rafal.naminggen.model.Name;
import com.zaleski.rafal.naminggen.presenter.View;

public interface NameListView extends View {

    void initRecyclerAdapter();

    void onNextItemList(PagedList<Name> pagedList);

    void clearListRecycler();

    void openFilterDialog(NameFilter nameFilter);

    void invalidateFilterText(int which);
}
