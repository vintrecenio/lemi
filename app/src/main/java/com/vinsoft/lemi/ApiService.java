package com.vinsoft.lemi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("cities")
    Single<ArrayList<Cities>> getCities();

    @GET("cities")
    Single<ArrayList<Cities>> searchCities(@Query("q") String city);

}
