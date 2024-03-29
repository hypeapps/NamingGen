package com.zaleski.rafal.nameshaker.features.namelist;

import android.arch.paging.PagedList;
import android.util.Log;

import com.zaleski.rafal.nameshaker.dataprovider.datasource.NameDataRepository;
import com.zaleski.rafal.nameshaker.model.Name;
import com.zaleski.rafal.nameshaker.model.NameFilter;
import com.zaleski.rafal.nameshaker.presenter.Presenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class NameListPresenter extends Presenter<NameListView> {

    private final NameDataRepository nameDataRepository;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public NameListPresenter(NameDataRepository nameDataRepository) {
        this.nameDataRepository = nameDataRepository;
    }

    @Override
    protected void onAttachView(NameListView view) {
        super.onAttachView(view);
        this.view.initRecyclerAdapter();
    }

    @Override
    protected void onDetachView() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        super.onDetachView();
    }

    public void onSubmitFilter(NameFilter nameFilter) {
        getNames(nameFilter);
    }

    private void getNames(NameFilter nameFilter) {
        if (nameFilter.isEachSex()) {
            disposables.add(nameDataRepository.getAllNames()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new NameListObserver()));
            this.view.invalidateFilterText(2);
        } else if (nameFilter.isFemale()) {
            disposables.add(nameDataRepository.getAllFemaleNames()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new NameListObserver()));
            this.view.invalidateFilterText(1);
        } else {
            disposables.add(nameDataRepository.getAllMaleNames()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new NameListObserver()));
            this.view.invalidateFilterText(0);
        }
        this.view.clearListRecycler();
    }

    public void onFilterButtonClick(NameFilter submitNameFilter) {
        this.view.openFilterDialog(submitNameFilter);
    }

    class NameListObserver extends DisposableSubscriber<PagedList<Name>> {

        @Override
        public void onNext(PagedList<Name> pagedList) {
            NameListPresenter.this.view.onNextItemList(pagedList);
        }

        @Override
        public void onError(Throwable t) {
            Log.e("ERROR ", t.getMessage());
        }

        @Override
        public void onComplete() {
        }
    }
}
