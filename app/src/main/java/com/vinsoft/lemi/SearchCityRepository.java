package com.vinsoft.lemi;

import android.util.Log;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchCityRepository {

    private ApiService apiService;
    private String mParam;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<ArrayList<Cities>> mutableCities = new MutableLiveData<>();

    public SearchCityRepository(CompositeDisposable compositeDisposable, String param) {

        this.compositeDisposable = compositeDisposable;
        this.mParam = param;

    }

    public MutableLiveData<ArrayList<Cities>> searchCities(){

        apiService = ServiceGenerator.createService(ApiService.class);

        apiService.searchCities(mParam)
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
