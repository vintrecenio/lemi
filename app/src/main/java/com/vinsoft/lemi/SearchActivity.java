package com.vinsoft.lemi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.vinsoft.lemi.viewmodels.CitySearchViewModel;
import com.vinsoft.lemi.viewmodels.CityViewModel;
import com.vinsoft.lemi.viewmodels.ViewModelFactory;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    CityAdapter cityAdapter;
    CityViewModel cityViewModel;
//    CitySearchViewModel citySearchViewModel;
    ArrayList<Cities> citiesArrayList = new ArrayList<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.rvCities)
    RecyclerView recyclerCities;

    @BindView(R.id.etSearch)
    EditText search;

    @BindView(R.id.pLoader)
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        initViews();

        initCities();

        recyclerCities.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                hideKeyboard();
            }
        });
    }

    @OnClick(R.id.ivBack) void onBack(){
        this.finish();
    }

    @OnTextChanged(R.id.etSearch) void searchCity(){
        if(search.length() > 0){
            searchCities(search.getText().toString());
        }else{
            initCities();
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void initViews(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCities.setHasFixedSize(true);
        recyclerCities.setLayoutManager(layoutManager);
    }

    private void initCities(){
            loader.setVisibility(View.VISIBLE);
            recyclerCities.setAdapter(null);
            cityViewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getApplication(), compositeDisposable)).get(CityViewModel.class);
            cityViewModel.getCities().observe(this, cities -> {
            cityAdapter = new CityAdapter(cities, SearchActivity.this);
            recyclerCities.setAdapter(cityAdapter);
            cityAdapter.notifyDataSetChanged();
            loader.setVisibility(View.GONE);
        });
    }

/*    private void searchCities(String params){
            citySearchViewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getApplication(), params)).get(CitySearchViewModel.class);
            citySearchViewModel.getCities().observe(this, cities -> {
            cityAdapter = new CityAdapter(cities, SearchActivity.this);
            recyclerCities.setAdapter(cityAdapter);
            cityAdapter.notifyDataSetChanged();
        });
    }*/

    private void searchCities(String param){

        recyclerCities.setAdapter(null);
        loader.setVisibility(View.VISIBLE);

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

                        citiesArrayList.clear();
                        citiesArrayList.addAll(cities);

                        loader.setVisibility(View.GONE);

                        cityAdapter = new CityAdapter(citiesArrayList, SearchActivity.this);
                        recyclerCities.setAdapter(cityAdapter);
                        cityAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                        loader.setVisibility(View.GONE);

                        Log.e("RETRO_ERR", e.toString());

                    }
                });
    }

}
