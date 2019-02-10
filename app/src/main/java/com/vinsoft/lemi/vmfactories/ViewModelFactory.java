package com.vinsoft.lemi.vmfactories;

import android.app.Application;

import com.vinsoft.lemi.viewmodels.CityViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.disposables.CompositeDisposable;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private CompositeDisposable mCompositeDisposable;

    public ViewModelFactory(Application application, CompositeDisposable compositeDisposable) {
        mApplication = application;
        mCompositeDisposable = compositeDisposable;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CityViewModel(mApplication, mCompositeDisposable);
    }
}
