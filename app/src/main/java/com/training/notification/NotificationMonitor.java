package com.training.notification;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqiuyi on 17/3/8.
 */

public class NotificationMonitor extends NotificationListenerService {
    private static final int POST = 0, REMOVE = 1;

    private static final int EVENT_UPDATE_CURRENT_NOS = 0;
    public static final String ACTION_NLS_CONTROL = "com.training.NLSCONTROL";
    public static String ACTION_LISTEN = "action_listen";


    //用于存储当前所有的Notification的StatusBarNotification对象数组
    public static List<StatusBarNotification[]> mCurrentNotifications = new ArrayList<StatusBarNotification[]>();

    public static int mCurrentNotificationsCounts = 0;
    //收到新通知后将通知的StatusBarNotification对象赋值给mPostedNotification
    public static StatusBarNotification mPostedNotification;
    //删除一个通知后将通知的StatusBarNotification对象赋值给mRemovedNotification
    public static StatusBarNotification mRemovedNotification;
    private CancelNotificationReceiver mReceiver = new CancelNotificationReceiver();

    private List<AppInfo> appInfos;
    private MyNotificationManager myNotificationManager;

    private boolean isOpen = true;

    private Handler mMonitorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_UPDATE_CURRENT_NOS:
                    updateCurrentNotifications();
                    break;
                default:
                    break;
            }
        }
    };

    class CancelNotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action;
            if (intent != null && intent.getAction() != null) {
                action = intent.getAction();
                if (action.equals(ACTION_NLS_CONTROL)) {
                    String command = intent.getStringExtra("command");
                    if (TextUtils.equals(command, "cancel_last")) {
                        if (mCurrentNotifications != null && mCurrentNotificationsCounts >= 1) {
                            //每次删除通知最后一个
                            StatusBarNotification sbnn = getCurrentNotifications()[mCurrentNotificationsCounts - 1];
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                cancelNotification(sbnn.getPackageName(), sbnn.getTag(), sbnn.getId());
                            } else {
                                cancelNotification(sbnn.getKey());
                            }
                        }
                    } else if (TextUtils.equals(command, "cancel_all")) {
                        //删除所有通知
                        cancelAllNotifications();
                    }
                } else if (action.equals(MyNotificationManager.ACTION_UPADTE_APP_DATA)) {
                    appInfos = myNotificationManager.getAppInfosFromDb();
                } else if (action.equals(ACTION_LISTEN)) {
                    isOpen = intent.getBooleanExtra(ACTION_LISTEN, true);
                    updateCurrentNotifications();
                    StatusBarNotification[] notifications = getCurrentNotifications();
                    for (StatusBarNotification notification : notifications) {
                        myNotificationManager.addMessage(notification.getPackageName(),
                                (String) notification.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT));
                    }
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e("NLS:onCreate...");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NLS_CONTROL);
        filter.addAction(MyNotificationManager.ACTION_UPADTE_APP_DATA);
        filter.addAction(ACTION_LISTEN);
        registerReceiver(mReceiver, filter);
        mMonitorHandler.sendMessage(mMonitorHandler.obtainMessage(EVENT_UPDATE_CURRENT_NOS));

//        myNotificationManager = MyNotificationManager.getInstance(getApplicationContext());
//        appInfos = myNotificationManager.getAppInfosFromDb();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.e("NLS:onBind...");
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        Logger.e("NLS:onDestroy...");
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (!isOpen) {
            super.onNotificationPosted(sbn);
            return;
        }
        logNotification(POST, sbn);
//        super.onNotificationPosted(sbn);
//        updateCurrentNotifications();
        mPostedNotification = sbn;
        //通过以下方式可以获取Notification的详细信息
        /*
         * Bundle extras = sbn.getNotification().extras; String
         * notificationTitle = extras.getString(Notification.EXTRA_TITLE);
         * Bitmap notificationLargeIcon = ((Bitmap)
         * extras.getParcelable(Notification.EXTRA_LARGE_ICON)); Bitmap
         * notificationSmallIcon = ((Bitmap)
         * extras.getParcelable(Notification.EXTRA_SMALL_ICON)); CharSequence
         * notificationText = extras.getCharSequence(Notification.EXTRA_TEXT);
         * CharSequence notificationSubText =
         * extras.getCharSequence(Notification.EXTRA_SUB_TEXT);
         * Log.i("SevenNLS", "notificationTitle:"+notificationTitle);
         * Log.i("SevenNLS", "notificationText:"+notificationText);
         * Log.i("SevenNLS", "notificationSubText:"+notificationSubText);
         * Log.i("SevenNLS",
         * "notificationLargeIcon is null:"+(notificationLargeIcon == null));
         * Log.i("SevenNLS",
         * "notificationSmallIcon is null:"+(notificationSmallIcon == null));
         */

//        Bundle extras = sbn.getNotification().extras;
//        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
//        Bitmap notificationIcon = extras.getParcelable(Notification.EXTRA_SMALL_ICON);
//        Bitmap notificationLargeIcon = extras.getParcelable(Notification.EXTRA_LARGE_ICON);
//        CharSequence notificationSubText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);
//        CharSequence notificationText = extras.getCharSequence(Notification.EXTRA_TEXT);
//
//        Logger.e("title: " + notificationTitle);
//        Logger.e("text: " + notificationText);
//        Logger.e("packageName: " + sbn.getPackageName());
//
//        if (appInfos != null) {
//            if (myNotificationManager.needListen(sbn.getPackageName())) {
//                myNotificationManager.addMessage(sbn.getPackageName(), (String) notificationText);
//                ContextUtils.showToast(getApplicationContext(), sbn.getPackageName());
//                cancelNotification(sbn.getKey());
//            }
//        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
//        myNotificationManager.removeMessage(sbn.getPackageName());
        if (!isOpen) {
            super.onNotificationRemoved(sbn);
            return;
        }
        logNotification(REMOVE, sbn);
//        super.onNotificationRemoved(sbn);
        //当有通知被删除后，更新mCurrentNotifications列表
//        updateCurrentNotifications();
        mRemovedNotification = sbn;
    }

    private void updateCurrentNotifications() {
        StatusBarNotification[] activeNos = getActiveNotifications();
        if (mCurrentNotifications.size() == 0) {
            mCurrentNotifications.add(null);
        }
        mCurrentNotifications.set(0, activeNos);
        mCurrentNotificationsCounts = activeNos.length;

        for (StatusBarNotification sbn : activeNos) {
            logNotification(POST, sbn);
        }
    }

    public static StatusBarNotification[] getCurrentNotifications() {
        if (mCurrentNotifications.size() == 0) {
            return null;
        }
        return mCurrentNotifications.get(0);
    }

    private void logNotification(int type, StatusBarNotification sbn) {
        String pkgName = sbn.getPackageName();
        String title = sbn.getNotification().extras.getString(Notification.EXTRA_TITLE);
        String content = sbn.getNotification().extras.getString(Notification.EXTRA_TEXT);
        String typeString = ((type == POST) ? "post" : "remove");

        Logger.e(typeString + "\n"
                + "pkgName:" + pkgName + "\n"
                + "title:" + title + "\n"
                + "content:" + content + "\n"
                + "groupKey:" + sbn.getGroupKey() + "\n"
                + "key:" + sbn.getKey() + "\n"
                + "id:" + sbn.getId() + "\n"
                + "tag:" + sbn.getTag() + "\n"
                + "isOngoing:" + sbn.isOngoing() + "\n"
                + "isGroup:" + sbn.isGroup() + "\n"
                + "isClearable:" + sbn.isClearable()
        );
    }
}
