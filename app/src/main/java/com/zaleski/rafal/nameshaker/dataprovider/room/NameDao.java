package com.zaleski.rafal.nameshaker.dataprovider.room;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.zaleski.rafal.nameshaker.model.Name;

import io.reactivex.Single;

@Dao
public interface NameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Name name);

    @Query("SELECT * FROM names WHERE id LIKE :id")
    Single<Name> getNameById(int id);

    @Query("SELECT * FROM names GROUP BY name ORDER BY name ASC")
    DataSource.Factory<Integer, Name> getAllNames();

    @Query("SELECT id, name, SUM(count) AS count, voivodeship, sex FROM names GROUP BY name ORDER BY count DESC")
    DataSource.Factory<Integer, Name> getAllNamesOderByCount();

    @Query("SELECT * FROM names WHERE sex LIKE :gender GROUP BY name ORDER BY name ASC")
    DataSource.Factory<Integer, Name> getNamesByGender(String gender);

    @Query("SELECT id, name, sum(count) AS count, voivodeship, sex FROM names WHERE sex LIKE :gender GROUP BY name ORDER BY count DESC")
    DataSource.Factory<Integer, Name> getAllNamesByGenderOrderByCount(String gender);

    @Query("SELECT id, name, SUM(count) AS count, voivodeship, sex FROM names WHERE sex LIKE :gender AND voivodeship LIKE :voivodeship GROUP BY name ORDER BY count DESC")
    DataSource.Factory<Integer, Name> getNamesByGenderAndVoivodeship(String gender, String voivodeship);

    @Query("SELECT id, name, SUM(count) AS count, voivodeship, sex FROM names WHERE voivodeship LIKE :voivodeship GROUP BY name ORDER BY count DESC")
    DataSource.Factory<Integer, Name> getAllNamesByVoivodeship(String voivodeship);
}
