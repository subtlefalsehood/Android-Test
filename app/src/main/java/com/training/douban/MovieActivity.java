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

import com.subtlefalsehood.base.utils.ContextUtils;
import com.training.BaseActivity;
import com.training.DouBanEvent;
import com.training.R;
import com.training.common.model.OnMultiTouchListener;
import com.training.douban.adapter.DouBanAdapter;
import com.training.network.DouBanModel;
import com.training.network.activity.WebActivity;
import com.training.network.model.EndlessRecyclerOnScrollListener;
import com.training.network.model.data.RpDBM250;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MovieActivity extends BaseActivity implements DouBanAdapter.OnItemClickListener {
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
    private DouBanModel mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban_test);
        EventBus.getDefault().register(this);
        mKnife = ButterKnife.bind(this);
        mManager = DouBanModel.getInstance();

        initToolbar();
        initView();
        initSwipeLayout();
        requestData(0, DouBanModel.DEFAULT_ONCE_REQUEST_COUNT);
    }

    private void requestData(int start, int count) {
        mManager.requestMovie(start, count);
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
                requestData(0, DouBanModel.DEFAULT_ONCE_REQUEST_COUNT);
            }
        });
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore() {
                if (mAdapter.getRpList().size() < 250) {
                    requestData(mAdapter.getStart(), DouBanModel.DEFAULT_ONCE_REQUEST_COUNT);
                } else {
                    ContextUtils.showSnack(mRecyclerView, "到底了-V-");
                }
            }
        });
    }

    private void showLoading() {
        if (mSwipeLayout != null && !mSwipeLayout.isRefreshing()) {
            mSwipeLayout.setRefreshing(true);
        }
    }

    private void hideLoading() {
        if (mSwipeLayout != null && mSwipeLayout.isRefreshing()) {
            mSwipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void onMovieItemClick(View v, int position) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", mAdapter.getRpList().get(position).getAlt());
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DouBanEvent event) {
        if (event != null) {
            switch (event.getMessage()) {
                case DouBanEvent.TOP250_MOVIES_START:
                    showLoading();
                    break;
                case DouBanEvent.TOP250_MOVIES_SUCCESS:
                    hideLoading();
                    handlerData(event);
                    break;
                case DouBanEvent.TOP250_MOVIES_ERROR:
                    hideLoading();
                    break;
            }
        }
    }

    private void handlerData(DouBanEvent event) {
        if (event.getData() != null && event.getData() instanceof RpDBM250) {
            RpDBM250 rpDBM250 = (RpDBM250) event.getData();
            if (rpDBM250.getStart() == 0) {
                mAdapter.setRpList(rpDBM250.getSubjects());
            } else {
                mAdapter.addRpList(rpDBM250.getSubjects());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mManager.destroy();
    }
}
