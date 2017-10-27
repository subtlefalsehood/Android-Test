package com.training.network;

import com.training.DouBanEvent;
import com.training.douban.UrlContant;
import com.training.network.model.data.RpDBM250;
import com.training.network.request.IDouBanService;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenqiuyi on 17/8/17.
 */

public class DouBanModel {
    private static DouBanModel mInstance;
    public static int DEFAULT_ONCE_REQUEST_COUNT = 50;
    private static int DEFAULT_TIMEOUT_SECOND = 20;

    public static DouBanModel getInstance() {
        if (mInstance == null) {
            mInstance = new DouBanModel();
        }
        return mInstance;
    }

    private DouBanModel() {

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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        Observable<RpDBM250> observable = retrofit.create(IDouBanService.class).getMovies(start, count);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RpDBM250>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        EventBus.getDefault().post(new DouBanEvent(DouBanEvent.TOP250_MOVIES_START));
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        EventBus.getDefault().post(new DouBanEvent(DouBanEvent.TOP250_MOVIES_ERROR, throwable));
                    }

                    @Override
                    public void onNext(RpDBM250 rpDBM250) {
                        EventBus.getDefault().post(new DouBanEvent(DouBanEvent.TOP250_MOVIES_SUCCESS, rpDBM250));
                    }
                });
    }

    public void destroy() {
        mInstance = null;
    }
}
