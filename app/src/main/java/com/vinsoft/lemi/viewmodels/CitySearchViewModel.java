package com.vinsoft.lemi.viewmodels;

import android.app.Application;
import android.util.Log;

import com.vinsoft.lemi.ApiService;
import com.vinsoft.lemi.Cities;
import com.vinsoft.lemi.SearchCityRepository;
import com.vinsoft.lemi.ServiceGenerator;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CitySearchViewModel extends AndroidViewModel {

    private SearchCityRepository cityRepository;

    public CitySearchViewModel(@NonNull Application application, CompositeDisposable compositeDisposable, String param) {
        super(application);

        cityRepository = new SearchCityRepository(compositeDisposable, param);

    }

    public MutableLiveData<ArrayList<Cities>> getCities(){
        return cityRepository.searchCities();
    }

}
