package com.training;

import android.app.Application;

import com.training.network.Constant;
import com.training.network.utils.SystemUtils;

/**
 * Created by chenqiuyi on 16/12/21.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        Constant.DEVICE_ID = SystemUtils
                .getDeviceId(getApplicationContext());
        super.onCreate();
    }
}
