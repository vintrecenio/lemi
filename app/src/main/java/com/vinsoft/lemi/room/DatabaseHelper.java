package com.vinsoft.lemi.room;


import android.content.Context;

import com.vinsoft.lemi.Cities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class DatabaseHelper {

    private CitiesDao citiesDao;

    public DatabaseHelper(Context context) {
        citiesDao = AppDatabase.getInstance(context).getCitiesDao();
    }

    public LiveData<List<Cities>> getCities(){
        return citiesDao.getAllCities();
    }

    public void inserCities(List<Cities> cities){
        citiesDao.saveAll(cities);
    }

}
