package com.vinsoft.lemi.vmfactories;

import android.app.Application;

import com.vinsoft.lemi.viewmodels.CitySearchViewModel;
import com.vinsoft.lemi.viewmodels.CityViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.disposables.CompositeDisposable;

public class ViewModelFactorySearch implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mParam;
    private CompositeDisposable mCompositeDisposable;

    public ViewModelFactorySearch(Application application, CompositeDisposable disposable, String param) {
        mApplication = application;
        mCompositeDisposable = disposable;
        mParam = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CitySearchViewModel(mApplication, mCompositeDisposable, mParam);
    }
}
