package com.training.service.model;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.training.IMyAidlService;

/**
 * Created by chenqiuyi on 16/10/24.
 */

public class SeviceManager {
    private static IMyAidlService sSevice = null;

    private static class ServiceBinder implements ServiceConnection {
        ServiceConnection mCallback;

        public ServiceBinder(ServiceConnection callback) {
            mCallback = callback;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (sSevice == null) {
                sSevice = IMyAidlService.Stub.asInterface(service);
            }
            if (mCallback != null) {
                mCallback.onServiceConnected(name, service);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (mCallback != null) {
                mCallback.onServiceDisconnected(name);
            }
        }
    }

}
