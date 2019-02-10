package com.vinsoft.lemi;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CityRepository {

    private ApiService apiService;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<ArrayList<Cities>> mutableCities = new MutableLiveData<>();

    public CityRepository(CompositeDisposable compositeDisposable) {

        this.compositeDisposable = compositeDisposable;

    }

    public LiveData<ArrayList<Cities>> getCities(){

        apiService = ServiceGenerator.createService(ApiService.class);

        apiService.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Cities>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ArrayList<Cities> cities) {
                        mutableCities.setValue(cities);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("RETRO_ERR", e.toString());

                    }
                });

        return mutableCities;
    }
}
