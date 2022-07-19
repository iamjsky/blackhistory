package com.blackhistory.api;



import com.blackhistory.model.MainBannerModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by steve on 2017. 11. 7..
 */

public interface ApiService {



    @GET("/_blackhistory/app/if001.php/")
    Observable<List<MainBannerModel>> getMainBanner();


}