package com.zaleski.rafal.naminggen.dataprovider.datasource;

import android.arch.paging.PagedList;

import com.zaleski.rafal.naminggen.model.Name;

import io.reactivex.Flowable;

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
}
