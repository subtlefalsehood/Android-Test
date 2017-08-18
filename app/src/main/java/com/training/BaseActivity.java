package com.training;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.Unbinder;

/**
 * Created by chenqiuyi on 17/3/7.
 */

public class BaseActivity extends AppCompatActivity {
    public Unbinder mKnife;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mKnife != null) {
            mKnife.unbind();
        }
    }
}
