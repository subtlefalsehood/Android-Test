package com.training;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chenqiuyi on 17/3/7.
 */

public class BaseActivity extends AppCompatActivity {
    public Context aContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aContext = getApplicationContext();
    }
}
