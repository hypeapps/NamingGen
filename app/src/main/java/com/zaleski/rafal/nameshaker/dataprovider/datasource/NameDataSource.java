package com.zaleski.rafal.nameshaker.dataprovider.datasource;

import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;

import com.zaleski.rafal.nameshaker.dataprovider.room.RoomService;
import com.zaleski.rafal.nameshaker.model.Name;

import java.util.Random;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;

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
    public Single<Name> getRandomNameByGender(String gender) {
        if (gender.equals("FEMALE")) {
            int x = 1;
            int y = 3500;
            int randomMaleNameId = new Random().nextInt(y - x + 1) + x;
            return roomService.nameDao().getNameById(randomMaleNameId);
        } else {
            int x = 3520;
            int y = 6800;
            int randomMaleNameId = new Random().nextInt(y - x + 1) + x;
            return roomService.nameDao().getNameById(randomMaleNameId);
        }
    }

    @Override
    public Single<Name> getRandomName() {
        int x = 1;
        int y = 6800;
        int randomMaleNameId = new Random().nextInt(y - x + 1) + x;
        return roomService.nameDao().getNameById(randomMaleNameId);
    }

    @Override
    public void insert(Name name) {
        roomService.nameDao().insert(name);
    }
}
