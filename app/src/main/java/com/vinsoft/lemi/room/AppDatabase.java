package com.vinsoft.lemi.room;

import android.content.Context;

import com.vinsoft.lemi.Cities;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = Cities.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database.db").allowMainThreadQueries().build();
        }

        return appDatabase;
    }

    public abstract CitiesDao getCitiesDao();
}
