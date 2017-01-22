package com.training.douban;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.training.R;
import com.training.common.model.OnMultiTouchListener;
import com.training.common.utlis.ContextUtils;
import com.training.douban.model.DouBanAdapter;
import com.training.network.activity.WebActivity;
import com.training.network.model.EndlessRecyclerOnScrollListener;
import com.training.network.model.RpDBM250;
import com.training.network.model.RpDBM250.Subject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MovieTestActivity extends AppCompatActivity implements DouBanAdapter.DoWhat {
    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.btn_start)
    View btn_start;

    @BindView(R.id.srl)
    SwipeRefreshLayout swipeLayout;

    private LinearLayoutManager linearLayoutManager;
    private DouBanAdapter adapter;
    private boolean isInitSWL = false;
    private RpDBM250 rpDBM250;
    private List<Subject> rpList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban_test);
        ButterKnife.bind(this);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        adapter = new DouBanAdapter(this, rpList, this);
        rv.setAdapter(adapter);
        findViewById(R.id.ll).setOnTouchListener(new OnMultiTouchListener() {
            @Override
            public boolean onMultiTouch(int count) {
                if (count == 2) {
                    rv.scrollToPosition(0);
                    return true;
                }
                return false;
            }
        });

        initSwipeLayout();
        test1(0, 10);
    }

    @Override
    public void onMovieItemClick(View v, int position) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", rpList.get(position).getAlt());
        startActivity(intent);
    }


    @OnClick({R.id.btn_start, R.id.ll})
    void onClick(View v) {
        switch (v.getId()) {

        }
    }

    //test rx+retrofit
    private interface DouBanMovieService {
        @GET(DoubanUrl.DOUBAN_MOVIE_TOP250)
        Observable<RpDBM250> getMovieWithRxJava(@Query("start") int start, @Query("count") int count);

        @GET("/v2/movie/top250")
        Call<RpDBM250> getMovieWithCall(@Query("start") int start, @Query("count") int count);
    }

    private void getDouBanMovieList(int start, int count) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DouBanMovieService douBanMovieService = retrofit.create(DouBanMovieService.class);
        Call<RpDBM250> call = douBanMovieService.getMovieWithCall(start, count);
        call.enqueue(new Callback<RpDBM250>() {
            @Override
            public void onResponse(Call<RpDBM250> call, Response<RpDBM250> response) {
                if (response != null) {
                    rpDBM250 = response.body();
                    rpList.addAll(response.body().getSubjects());
                    adapter.notifyDataSetChanged();
                    if (!isInitSWL) {
                        initSwipeLayout();
                    } else {
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    }
                    test(rpDBM250);
                }
            }

            @Override
            public void onFailure(Call<RpDBM250> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void test1(int start, int count) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DoubanUrl.DOUBAN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        final DouBanMovieService serviceTest = retrofit.create(DouBanMovieService.class);
        serviceTest.getMovieWithRxJava(start, count)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RpDBM250>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ContextUtils.showToast(MovieTestActivity.this, e.toString());
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(RpDBM250 rpDBM250Test) {
                        rpDBM250 = rpDBM250Test;
                        rpList.addAll(rpDBM250.getSubjects());
                        adapter.notifyDataSetChanged();
                        if (!isInitSWL) {
                            isInitSWL = true;
                            btn_start.setVisibility(View.GONE);
                            title.setText(rpDBM250.getTitle());
                            title.setVisibility(View.VISIBLE);
                        }
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    }
                });
    }


    private void initSwipeLayout() {
        swipeLayout.setRefreshing(true);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
            }
        });
        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (!swipeLayout.isRefreshing()) {
                    if (rpDBM250 != null) {
                        if (rpDBM250.getStart() + rpDBM250.getCount() <= 240) {
                            test1(rpDBM250.getStart() + rpDBM250.getCount(), 10);
                        } else if (rpDBM250.getStart() + rpDBM250.getCount() < 250) {
                            test1(rpDBM250.getStart() + rpDBM250.getCount()
                                    , 250 - (rpDBM250.getStart() + rpDBM250.getCount()));
                        } else {
                            ContextUtils.showSnack(rv, "到底了-V-");
                            return;
                        }
                    }
                    swipeLayout.setRefreshing(true);
                }
            }
        });
    }


    private void test(RpDBM250 rpDBM250) {
        Observable.just(rpDBM250)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<RpDBM250, Observable<String>>() {
                    @Override
                    public Observable<String> call(RpDBM250 rpDBM250) {
                        return Observable.from(rpDBM250.getSubjects())
                                .flatMap(new Func1<Subject, Observable<String>>() {
                                    @Override
                                    public Observable<String> call(Subject subject) {
                                        return Observable.just(subject.getTitle());
                                    }
                                });
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        ContextUtils.showToast(MovieTestActivity.this, s);
                    }
                });
    }
}
