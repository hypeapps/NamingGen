package com.zaleski.rafal.nameshaker.features.statisticdata;

import android.arch.paging.PagedList;
import android.util.Log;

import com.zaleski.rafal.nameshaker.dataprovider.datasource.NameDataRepository;
import com.zaleski.rafal.nameshaker.model.Name;
import com.zaleski.rafal.nameshaker.model.NameFilter;
import com.zaleski.rafal.nameshaker.presenter.Presenter;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class StatisticDataPresenter extends Presenter<StatisticDataView> {

    private final NameDataRepository nameDataRepository;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public StatisticDataPresenter(NameDataRepository nameDataRepository) {
        this.nameDataRepository = nameDataRepository;
    }

    @Override
    protected void onAttachView(StatisticDataView view) {
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
        // Each gender and each voivodeship
        if (nameFilter.isEachSex() && nameFilter.getVoivodeship().equals("Wszystkie")) {
            subscribeFlowable(nameDataRepository.getAllNamesOrderByCount());
            this.view.invalidateFilterText(2, nameFilter.getVoivodeship());
            // Each gender and specific voivodeship
        } else if (nameFilter.isEachSex() && !nameFilter.getVoivodeship().equals("Wszystkie")) {
            subscribeFlowable(nameDataRepository.getAllNamesByVoivodeshop(nameFilter.getVoivodeship()));
            this.view.invalidateFilterText(2, nameFilter.getVoivodeship());
            // Female gender and each voivodeship
        } else if (nameFilter.isFemale() && nameFilter.getVoivodeship().equals("Wszystkie")) {
            subscribeFlowable(nameDataRepository.getAllFemaleNamesOrderByCount());
            this.view.invalidateFilterText(1, nameFilter.getVoivodeship());
            // Female gender and specific voivodeship
        } else if (nameFilter.isFemale() && !nameFilter.getVoivodeship().equals("Wszystkie")) {
            subscribeFlowable(nameDataRepository.getNamesByGenderAndVoivodeship("FEMALE", nameFilter.getVoivodeship()));
            this.view.invalidateFilterText(1, nameFilter.getVoivodeship());
            // Male gender and each voivodeship
        } else if (nameFilter.isMale() && nameFilter.getVoivodeship().equals("Wszystkie")) {
            subscribeFlowable(nameDataRepository.getAllMaleNamesOrderByCount());
            this.view.invalidateFilterText(0, nameFilter.getVoivodeship());
            // Male gender and specific voivodeship
        } else if (nameFilter.isMale() && !nameFilter.getVoivodeship().equals("Wszystkie")) {
            subscribeFlowable(nameDataRepository.getNamesByGenderAndVoivodeship("MALE", nameFilter.getVoivodeship()));
            this.view.invalidateFilterText(0, nameFilter.getVoivodeship());
        }
        this.view.clearListRecycler();
    }

    public void onFilterButtonClick(NameFilter submitNameFilter) {
        this.view.openFilterGenderDialog(submitNameFilter);
    }

    private void subscribeFlowable(Flowable<PagedList<Name>> flowable) {
        disposables.add(flowable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new NameListObserver()));
    }

    class NameListObserver extends DisposableSubscriber<PagedList<Name>> {

        @Override
        public void onNext(PagedList<Name> pagedList) {
            StatisticDataPresenter.this.view.onNextItemList(pagedList);
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
