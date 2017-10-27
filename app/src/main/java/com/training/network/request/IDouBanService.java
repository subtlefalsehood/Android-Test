package com.training.network.request;

import com.training.douban.UrlContant;
import com.training.network.model.data.RpDBM250;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chenqiuyi on 17/8/17.
 */

public interface IDouBanService {
    @GET(UrlContant.DOUBAN_MOVIE_TOP250)
    Observable<RpDBM250> getMovies(@Query("start") int start, @Query("count") int count);
}
