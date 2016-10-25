package com.training;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chenqiuyi on 16/10/24.
 */

public class MyActivity extends AppCompatActivity {

    public Context mContext;
    public Context aContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        aContext = getApplicationContext();
    }
}
