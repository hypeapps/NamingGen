package com.zaleski.rafal.nameshaker.dataprovider.datasource;

import android.arch.paging.PagedList;

import com.zaleski.rafal.nameshaker.model.Name;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface NameDataRepository {

    void insert(Name name);

    Flowable<PagedList<Name>> getAllNames();

    Flowable<PagedList<Name>> getAllNamesOrderByCount();

    Flowable<PagedList<Name>> getAllMaleNamesOrderByCount();

    Flowable<PagedList<Name>> getAllFemaleNamesOrderByCount();

    Flowable<PagedList<Name>> getAllNamesByVoivodeshop(String voivodeship);

    Flowable<PagedList<Name>> getNamesByGenderAndVoivodeship(String gender, String voivodeship);

    Flowable<PagedList<Name>> getAllFemaleNames();

    Flowable<PagedList<Name>> getAllMaleNames();

    Single<Name> getRandomNameByGender(String gender);

    Single<Name> getRandomName();
}
