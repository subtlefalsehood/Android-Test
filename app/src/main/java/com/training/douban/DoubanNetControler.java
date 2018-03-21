package com.training.douban;

import com.training.main.model.DoubanMovieBean;
import com.training.network.request.IDouBanService;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chenqiuyi on 17/8/17.
 */

public class DoubanNetControler {
    private static DoubanNetControler mInstance;
    public static int DEFAULT_ONCE_REQUEST_COUNT = 50;
    private static int DEFAULT_TIMEOUT_SECOND = 20;

    public static DoubanNetControler getInstance() {
        if (mInstance == null) {
            mInstance = new DoubanNetControler();
        }
        return mInstance;
    }

    private DoubanNetControler() {

    }

    public void requestMovie(int start, int count) {
        OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                        .connectTimeout(DEFAULT_TIMEOUT_SECOND, TimeUnit.SECONDS)
                        .readTimeout(DEFAULT_TIMEOUT_SECOND, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT_SECOND, TimeUnit.SECONDS)
                        .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlContant.DOUBAN_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        Observable<DoubanMovieBean> observable = retrofit.create(IDouBanService.class).getMovies(start, count);

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DoubanMovieBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        EventBus.getDefault().post(new DouBanEvent(DouBanEvent.TOP250_MOVIES_START));
                    }

                    @Override
                    public void onNext(DoubanMovieBean doubanMovieBean) {
                        EventBus.getDefault().post(new DouBanEvent(DouBanEvent.TOP250_MOVIES_SUCCESS, doubanMovieBean));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        EventBus.getDefault().post(new DouBanEvent(DouBanEvent.TOP250_MOVIES_ERROR, throwable));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void destroy() {
        mInstance = null;
    }
}
