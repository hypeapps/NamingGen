package com.zaleski.rafal.naminggen.dataprovider.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.zaleski.rafal.naminggen.model.Name;

@Database(entities = {Name.class}, version = 1)
public abstract class RoomService extends RoomDatabase {

    private static RoomService INSTANCE;

    public abstract NameDao nameDao();

    public static RoomService getAppDatabase(Context context, String databaseName) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RoomService.class, databaseName)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}

