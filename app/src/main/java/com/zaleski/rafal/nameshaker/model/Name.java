package com.zaleski.rafal.nameshaker.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

@Entity(tableName = "names")
public class Name {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "count")
    private Integer count;

    @ColumnInfo(name = "voivodeship")
    private String voivodeship;

    @ColumnInfo(name = "sex")
    private String sex;

    public Name(Integer id, String name, String voivodeship) {
        this.id = id;
        this.name = name;
        this.voivodeship = voivodeship;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public static final DiffUtil.ItemCallback<Name> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Name>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Name oldUser, @NonNull Name newUser) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.getId() == newUser.getId();
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull Name oldUser, @NonNull Name newUser) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUser.equals(newUser);
                }
            };
}
