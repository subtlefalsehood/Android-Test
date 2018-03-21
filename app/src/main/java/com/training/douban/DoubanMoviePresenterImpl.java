package com.training.douban;

import android.content.Context;

import com.training.main.model.DoubanMovieBean;
import com.training.network.request.IDouBanService;

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
 * Created by chenqiuyi on 2018/1/19.
 */

public class DoubanMoviePresenterImpl implements DoubanContrack.Presenter {
    public static int DEFAULT_ONCE_REQUEST_COUNT = 50;
    private static int DEFAULT_TIMEOUT_SECOND = 20;

    private Context mContext;
    private DoubanContrack.View mView;


    public DoubanMoviePresenterImpl(Context mContext, DoubanContrack.View mView) {
        this.mContext = mContext;
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        requestMovie(0, DoubanNetControler.DEFAULT_ONCE_REQUEST_COUNT);
    }

    @Override
    public void unSubscribe() {

    }

    @Override
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
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(DoubanMovieBean doubanMovieBean) {
                        mView.hideLoading();
                        mView.updateView(doubanMovieBean);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
