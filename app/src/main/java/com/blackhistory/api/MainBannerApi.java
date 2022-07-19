package com.blackhistory.api;


import com.blackhistory.ServiceGenerator;
import com.blackhistory.model.MainBannerModel;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by steve on 2017. 12. 6..
 */

public class MainBannerApi {
    public static Observable<List<MainBannerModel>> getMainBanner() {
        ApiService apiService = ServiceGenerator.generate(ApiService.class);
        return apiService.getMainBanner().subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread());
    }





}
