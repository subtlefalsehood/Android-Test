package com.training.main;

import android.content.Context;

import com.training.R;

import java.util.ArrayList;

/**
 * Created by chenqiuyi on 2017/12/25.
 */

public class MainPresenterImpl implements MainContrack.Presenter {

    private Context mContext;
    private MainContrack.View mView;

    public MainPresenterImpl(Context context, MainContrack.View view) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
    }

    private void fillData() {
        ArrayList<MainItemBean> mainList = new ArrayList<MainItemBean>() {{
            add(new MainItemBean(R.string.service, R.id.btn_service));
            add(new MainItemBean(R.string.network, R.id.btn_network));
            add(new MainItemBean(R.string.md, R.id.btn_md));
            add(new MainItemBean(R.string.data, R.id.btn_data));
            add(new MainItemBean(R.string.notification_listen, R.id.btn_notification));
            add(new MainItemBean(R.string.douban, R.id.btn_douban));
        }};
        mView.setItems(mainList);
    }

    @Override
    public void subscribe() {
        fillData();
    }

    @Override
    public void unSubscribe() {

    }
}
