package com.vinsoft.lemi.room;

import com.vinsoft.lemi.Cities;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<Cities> cities);

    @Query("SELECT * FROM cities")
    LiveData<List<Cities>> getAllCities();
}
