package com.zaleski.rafal.nameshaker.features.randomname;

import android.util.Log;

import com.zaleski.rafal.nameshaker.dataprovider.datasource.NameDataRepository;
import com.zaleski.rafal.nameshaker.model.Name;
import com.zaleski.rafal.nameshaker.model.NameFilter;
import com.zaleski.rafal.nameshaker.presenter.Presenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RandomNamePresenter extends Presenter<RandomNameView> {

    private final NameDataRepository nameDataRepository;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public RandomNamePresenter(NameDataRepository nameDataRepository) {
        this.nameDataRepository = nameDataRepository;
    }

    @Override
    protected void onAttachView(RandomNameView view) {
        super.onAttachView(view);
    }

    @Override
    protected void onDetachView() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        super.onDetachView();
    }

    public void randomizeName(NameFilter nameFilter) {
        if (nameFilter.isEachSex()) {
            getRandomName();
        } else if (nameFilter.isMale()) {
            getRandomNameByGender("MALE");
        } else {
            getRandomNameByGender("FEMALE");
        }
        this.view.startCubeAnimation();
    }

    public void onFilterButtonClick(NameFilter nameFilter) {
        this.view.openFilterDialog(nameFilter);
    }

    public void onSubmitFilter(NameFilter submitNameFilter) {
        if (submitNameFilter.isEachSex()) {
            this.view.invalidateFilterText(2);
        } else if (submitNameFilter.isMale()) {
            this.view.invalidateFilterText(0);
        } else {
            this.view.invalidateFilterText(1);
        }
    }

    private void getRandomNameByGender(String gender) {
        disposables.add(nameDataRepository.getRandomNameByGender(gender)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RandomNameObserver())
        );
    }

    private void getRandomName() {
        disposables.add(nameDataRepository.getRandomName()
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RandomNameObserver())
        );
    }

    class RandomNameObserver extends DisposableSingleObserver<Name> {
        @Override
        public void onSuccess(Name name) {
            RandomNamePresenter.this.view.setRandomName(name.getName());
            RandomNamePresenter.this.view.stopCubeAnimation();
        }

        @Override
        public void onError(Throwable e) {
            Log.e("ERROR", e.getMessage());
        }
    }
}
