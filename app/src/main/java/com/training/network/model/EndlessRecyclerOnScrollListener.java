package com.training.network.model;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends
        RecyclerView.OnScrollListener {

    private boolean loading = false;
    private int lastVisibleItem, visibleItemCount, totalItemCount, preciousItemCount;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(
            LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
//            visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        lastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

        if (preciousItemCount < totalItemCount) {
            loading = false;
        }

        if (lastVisibleItem > totalItemCount - 2) {
            if (!loading) {
                preciousItemCount = totalItemCount;
                loading = true;
                onLoadMore();
            }
        }

    }

    public abstract void onLoadMore();
}