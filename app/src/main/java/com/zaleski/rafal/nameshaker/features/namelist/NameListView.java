package com.zaleski.rafal.nameshaker.features.namelist;

import android.arch.paging.PagedList;

import com.zaleski.rafal.nameshaker.model.Name;
import com.zaleski.rafal.nameshaker.model.NameFilter;
import com.zaleski.rafal.nameshaker.presenter.View;

public interface NameListView extends View {

    void initRecyclerAdapter();

    void onNextItemList(PagedList<Name> pagedList);

    void clearListRecycler();

    void openFilterDialog(NameFilter nameFilter);

    void invalidateFilterText(int which);
}
