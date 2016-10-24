package com.training.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.training.IMyAidlService;
import com.training.R;
import com.training.service.MyService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceTestActivity extends AppCompatActivity {
    private IMyAidlService sService;
    private MyService.MyBinder binder;
    private Intent intent;
    private boolean isConnected1 = false, isConnected2 = false;
    private ServiceConnection connection1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isConnected1 = true;
            sService = IMyAidlService.Stub.asInterface(service);
            try {
                sService.startDownload();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected1 = false;
            Logger.d("connect1 Disconnected");
        }
    };

    private ServiceConnection connection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isConnected2 = true;
            binder = (MyService.MyBinder) service;
            binder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected2 = false;
            Logger.d("connect2 Disconnected()");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ButterKnife.bind(this);
    }

    public void init() {
        intent = new Intent(this, MyService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isConnected1) {
            isConnected1 = false;
            unbindService(connection1);
        }
        if (isConnected2) {
            isConnected2 = false;
            unbindService(connection2);
        }
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_bind1, R.id.btn_bind2, R.id.btn_unbind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
//                Logger.d("start");
                startService(intent);
                break;
            case R.id.btn_stop:
//                Logger.d("stop");
                stopService(intent);
                break;
            case R.id.btn_bind1:
//                Logger.d("bind");
                if (isConnected2) {
                    unbindService(connection2);
                    isConnected2 = false;
                }
                if (!isConnected1) {
                    intent.putExtra("isAIDL", true);
                    bindService(intent, connection1, Service.BIND_AUTO_CREATE);
                }
                break;
            case R.id.btn_bind2:
                if (isConnected1) {
                    unbindService(connection1);
                    isConnected1 = false;
                }
                if (!isConnected2) {
                    intent.putExtra("isAIDL", false);
                    bindService(intent, connection2, Service.BIND_AUTO_CREATE);
                }
                break;
            case R.id.btn_unbind:
//                Logger.d("unbind");
                if (isConnected1) {
                    isConnected1 = false;
                    unbindService(connection1);
                }
                if (isConnected2) {
                    isConnected2 = false;
                    unbindService(connection2);
                }
                break;
        }
    }
}
