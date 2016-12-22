package com.training.network.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.training.R;
import com.training.common.utlis.ContextUtils;
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
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class DouBanTestActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_dou_ban_test);
        ButterKnife.bind(this);
//        StaggeredGridLayoutManager staggeredGridLayoutManager =
//                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        rv.setLayoutManager(staggeredGridLayoutManager);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        adapter = new DouBanAdapter();
        rv.setAdapter(adapter);
    }

    @OnClick(R.id.btn_start)
    void start() {
        getDouBanMovieList(0, 10);
    }

    private class DouBanAdapter extends RecyclerView.Adapter<DouBanAdapter.DBHolder> {

        @Override
        public DBHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(DouBanTestActivity.this)
                    .inflate(R.layout.activity_douban_item, parent, false);
            DBHolder holder = new DBHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(DBHolder holder, int position) {
            holder.textView.setText(rpList.get(position).getTitle());
            Picasso.with(DouBanTestActivity.this)
                    .load(rpList.get(position).getImages().getLarge())
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return rpList.size();
        }

        class DBHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView imageView;

            DBHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.tv_title);
                imageView = (ImageView) itemView.findViewById(R.id.img_movie);
            }
        }
    }


    private void getDouBanMovieList(int start, int count) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DouBanMovieService douBanMovieService = retrofit.create(DouBanMovieService.class);
        Call<RpDBM250> call = douBanMovieService.getMovie(start, count);
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
                }
            }

            @Override
            public void onFailure(Call<RpDBM250> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private interface DouBanMovieService {
        @GET("/v2/movie/top250")
        Call<RpDBM250> getMovie(@Query("start") int start, @Query("count") int count);
    }

    private void initSwipeLayout() {
        isInitSWL = true;
        btn_start.setVisibility(View.GONE);
        title.setText(rpDBM250.getTitle());
        title.setVisibility(View.VISIBLE);
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
                            getDouBanMovieList(rpDBM250.getStart() + rpDBM250.getCount(), 10);
                        } else if (rpDBM250.getStart() + rpDBM250.getCount() < 250) {
                            getDouBanMovieList(rpDBM250.getStart() + rpDBM250.getCount()
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
}
