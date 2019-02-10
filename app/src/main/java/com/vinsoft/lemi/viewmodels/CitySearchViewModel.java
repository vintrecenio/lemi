package com.vinsoft.lemi.viewmodels;

import android.app.Application;
import android.util.Log;

import com.vinsoft.lemi.ApiService;
import com.vinsoft.lemi.Cities;
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

    private MutableLiveData<ArrayList<Cities>> mutableCities = new MutableLiveData<>();;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CitySearchViewModel(@NonNull Application application, String param) {
        super(application);

        ApiService apiService = ServiceGenerator.createService(ApiService.class);

        apiService.searchCities(param)
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
    }

    public LiveData<ArrayList<Cities>> getCities(){
        return mutableCities;
    }

}
