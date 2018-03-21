package com.training.douban;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.subtlefalsehood.base.utils.JumpUtils;
import com.training.BaseActivity;
import com.training.R;
import com.training.common.model.OnMultiTouchListener;
import com.training.douban.adapter.DouBanAdapter;
import com.training.main.model.DoubanMovieBean;
import com.training.main.view.EndlessRecyclerOnScrollListener;
import com.training.network.activity.WebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MovieActivity extends BaseActivity implements DouBanAdapter.OnItemClickListener, DoubanContrack.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeLayout;

    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    @BindView(R.id.activity_dou_ban_test)
    DrawerLayout mDrawerLayout;

    private LinearLayoutManager mLinearLayoutManager;
    private DouBanAdapter mAdapter;


    private DoubanContrack.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban_test);
        mKnife = ButterKnife.bind(this);

        initToolbar();
        initView();
        initSwipeLayout();

        mPresenter = new DoubanMoviePresenterImpl(this, this);
        mPresenter.subscribe();
    }


    @OnClick(R.id.fab)
    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.START);
    }

    private void initView() {
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new DouBanAdapter(this);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setOnTouchListener(new OnMultiTouchListener() {
            @Override
            public boolean onMultiTouch(int count) {
                if (count == 2) {
                    mRecyclerView.scrollToPosition(0);
                    return true;
                }
                return false;
            }
        });
    }

    private void initSwipeLayout() {
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestMovie(0, DoubanNetControler.DEFAULT_ONCE_REQUEST_COUNT);
            }
        });
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore() {
                if (mAdapter.getRpList().size() < 250) {
                    mPresenter.requestMovie(mAdapter.getStart(), DoubanNetControler.DEFAULT_ONCE_REQUEST_COUNT);
                } else {
                    JumpUtils.showSnack(mRecyclerView, "到底了-V-");
                }
            }
        });
    }

    @Override
    public void showLoading() {
        if (mSwipeLayout != null && !mSwipeLayout.isRefreshing()) {
            mSwipeLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (mSwipeLayout != null && mSwipeLayout.isRefreshing()) {
            mSwipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void updateView(DoubanMovieBean doubanMovieBean) {
        if (doubanMovieBean != null) {
            if (doubanMovieBean.getStart() == 0) {
                mAdapter.setRpList(doubanMovieBean.getSubjects());
            } else {
                mAdapter.addRpList(doubanMovieBean.getSubjects());
            }
        }
    }

    @Override
    public void onMovieItemClick(View v, int position) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", mAdapter.getRpList().get(position).getAlt());
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }


    @Override
    public void setPresenter(DoubanContrack.Presenter presenter) {
        mPresenter = presenter;
    }
}
