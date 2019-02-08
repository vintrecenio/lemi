package com.vinsoft.lemi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ApiService apiService;
    CityAdapter cityAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.rvCities)
    RecyclerView recyclerCities;

    @BindView(R.id.etSearch)
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        initViews();

        apiService = ServiceGenerator.createService(ApiService.class);

        getInitialCities();

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
            getInitialCities();
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

    private void getInitialCities()
    {
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

                        cityAdapter = new CityAdapter(cities, SearchActivity.this);
                        recyclerCities.setAdapter(cityAdapter);
                        cityAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("RETRO_ERR", e.toString());

                    }
                });
    }

    private void searchCities(String param)
    {
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

                        cityAdapter = new CityAdapter(cities, SearchActivity.this);
                        recyclerCities.setAdapter(cityAdapter);
                        cityAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("SEARCH_ERR", e.toString());

                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }
}
