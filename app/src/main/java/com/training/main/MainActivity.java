package com.training.main;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.training.BaseActivity;
import com.training.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContrack.View {
    @BindView(R.id.main_recyclerview)
    RecyclerView mRecyclerView;

    private MainContrack.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mKnife = ButterKnife.bind(this);
        setupView();

        mPresenter = new MainPresenterImpl(this, this);
        mPresenter.subscribe();

    }

    private void setupView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        mPresenter = null;
    }

    @Override
    public void setItems(List<MainItemBean> items) {
        mRecyclerView.setAdapter(new MainAdapt((ArrayList<MainItemBean>) items));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void setPresenter(MainContrack.Presenter presenter) {
//        mPresenter = presenter;
    }
}
