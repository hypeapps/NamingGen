package com.zaleski.rafal.naminggen.dataprovider.datasource;

import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;

import com.zaleski.rafal.naminggen.dataprovider.room.RoomService;
import com.zaleski.rafal.naminggen.model.Name;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class NameDataSource implements NameDataRepository {

    private static final String MALE = "MALE";

    private static final String FEMALE = "FEMALE";

    private static final int SIZE_PAGE = 30;

    private RoomService roomService;

    @Inject
    public NameDataSource(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public Flowable<PagedList<Name>> getAllNames() {
        return new RxPagedListBuilder(roomService.nameDao().getAllNames(), SIZE_PAGE)
                .buildFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<PagedList<Name>> getAllNamesOrderByCount() {
        return new RxPagedListBuilder(roomService.nameDao().getAllNamesOderByCount(), SIZE_PAGE)
                .buildFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<PagedList<Name>> getAllNamesByVoivodeshop(String voivodeship) {
        return new RxPagedListBuilder(roomService.nameDao().getAllNamesByVoivodeship(voivodeship), SIZE_PAGE)
                .buildFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<PagedList<Name>> getAllFemaleNames() {
        return new RxPagedListBuilder(roomService.nameDao().getNamesByGender(FEMALE), SIZE_PAGE)
                .buildFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<PagedList<Name>> getAllMaleNames() {
        return new RxPagedListBuilder(roomService.nameDao().getNamesByGender(MALE), SIZE_PAGE)
                .buildFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<PagedList<Name>> getAllMaleNamesOrderByCount() {
        return new RxPagedListBuilder(roomService.nameDao().getAllNamesByGenderOrderByCount("MALE"), SIZE_PAGE)
                .buildFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<PagedList<Name>> getAllFemaleNamesOrderByCount() {
        return new RxPagedListBuilder(roomService.nameDao().getAllNamesByGenderOrderByCount("FEMALE"), SIZE_PAGE)
                .buildFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<PagedList<Name>> getNamesByGenderAndVoivodeship(String gender, String voivodeship) {
        return new RxPagedListBuilder(roomService.nameDao().getNamesByGenderAndVoivodeship(gender, voivodeship), SIZE_PAGE)
                .buildFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    public void insert(Name name) {
        roomService.nameDao().insert(name);
    }
}
