package com.training;

import android.app.Application;

import com.training.network.consts.UrlConstant;
import com.training.network.utils.SystemUtils;

/**
 * Created by chenqiuyi on 16/12/21.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        UrlConstant.DEVICE_ID = SystemUtils
                .getDeviceId(getApplicationContext());
        super.onCreate();
    }
}
