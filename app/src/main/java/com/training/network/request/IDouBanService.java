package com.training.network.request;

import com.training.douban.UrlContant;
import com.training.main.model.DoubanMovieBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chenqiuyi on 17/8/17.
 */

public interface IDouBanService {
    @GET(UrlContant.DOUBAN_MOVIE_TOP250)
    Observable<DoubanMovieBean> getMovies(@Query("start") int start, @Query("count") int count);
}
