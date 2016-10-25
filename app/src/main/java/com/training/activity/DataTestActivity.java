package com.training.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.training.MyActivity;
import com.training.R;
import com.training.utils.ContextUtils;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataTestActivity extends MyActivity {
    @BindViews({R.id.btn_content_provider, R.id.btn_database, R.id.btn_share_preference, R.id.btn_file})
    Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_content_provider, R.id.btn_database, R.id.btn_share_preference, R.id.btn_file})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_content_provider:
//                ContextUtils.jumpActivity(mContext, DataBaseTestActivity.class, false);
                break;
            case R.id.btn_database:
                ContextUtils.jumpFragment(getFragmentManager(), R.id.activity_data, new DatabaseTestFragment());
                break;
            case R.id.btn_share_preference:
//                ContextUtils.jumpActivity(mContext, DataBaseTestActivity.class, false);
                break;
            case R.id.btn_file:
//                ContextUtils.jumpActivity(mContext, DataBaseTestActivity.class, false);
                break;
        }
    }
}