package com.training.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.orhanobut.logger.Logger;
import com.training.IMyAidlService;

public class MyService extends Service {
    public static final String TAG = "MyService";
    public IMyAidlService.Stub mBinder1 = new IMyAidlService.Stub() {
        @Override
        public void startDownload() throws RemoteException {
            Logger.d("AIDL startDownload() executed");
        }
    };
    private MyBinder mBinder2 = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("onCreate() executed");
        //前台service
//        Notification notification = new Notification.Builder(this)
//                .setSmallIcon(R.drawable.ic_wing_1)
//                .setAutoCancel(true)
//                .setTicker("通知")
//                .setContentTitle("标题")
//                .setContentText("通知2")
//                .setWhen(System.currentTimeMillis())
//                .setDefaults(Notification.DEFAULT_ALL)
//                .build();
//        Intent notificationIntent = new Intent(this, ServiceTestActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        startForeground(1, notification);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Logger.d("stop()");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (intent.getBooleanExtra("isAIDL", false)) {
            return mBinder1;
        } else {
            return mBinder2;
        }
    }

    public class MyBinder extends Binder {
        public void startDownload() {
            Logger.d("MyBinder startDownload()");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }).start();
        }
    }

}
