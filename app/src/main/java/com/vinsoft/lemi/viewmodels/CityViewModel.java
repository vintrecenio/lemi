package com.vinsoft.lemi.viewmodels;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.vinsoft.lemi.ApiService;
import com.vinsoft.lemi.Cities;
import com.vinsoft.lemi.CityRepository;
import com.vinsoft.lemi.SearchActivity;
import com.vinsoft.lemi.ServiceGenerator;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CityViewModel extends AndroidViewModel {

    private CityRepository cityRepository;


    public CityViewModel(@NonNull Application application, CompositeDisposable compositeDisposable) {
        super(application);

        cityRepository = new CityRepository(compositeDisposable);

    }

    public LiveData<ArrayList<Cities>> getCities(){
        return cityRepository.getCities();
    }
}
