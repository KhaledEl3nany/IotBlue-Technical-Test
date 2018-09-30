package com.assem.tests.iotblue.Networking;

import com.assem.tests.iotblue.App.AppConfig;
import com.assem.tests.iotblue.Models.BookmarkModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET(".")
    Call<BookmarkModel> getBookmarkData(@Query(AppConfig.LAT) String lat,
                                        @Query(AppConfig.LON) String lan,
                                        @Query(AppConfig.APP_ID) String appId,
                                        @Query(AppConfig.UNITS) String units);
}
