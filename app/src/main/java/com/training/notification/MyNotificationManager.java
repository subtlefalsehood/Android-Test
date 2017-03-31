package com.training.notification;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Handler;
import android.os.HandlerThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenqiuyi on 17/3/14.
 */

public class MyNotificationManager {
    public static String ACTION_UPADTE_APP_DATA = "action_update_app_data";
    public static String ACTION_UPADTE_MESSAGE = "action_update_message";
    private static MyNotificationManager myNotificationManager;

    public static MyNotificationManager getInstance(Context context) {
        if (myNotificationManager == null) {
            myNotificationManager = new MyNotificationManager(context);
        }
        return myNotificationManager;
    }


    private HandlerThread mAppWorkThread;
    private Handler mAppWorkHandler;
    private Context mContext;
    private SQLiteOpenHelper sqLiteOpenHelper;

    public ArrayList<String> getPackageNameList() {
        return packageNameList;
    }

    private ArrayList<String> packageNameList = new ArrayList<>();

    private Map<String, String> messageInfos;

    private MyNotificationManager(Context context) {
        mContext = context.getApplicationContext();
        mAppWorkThread = new HandlerThread("com.training.packagemange.AppWorkThread");
        mAppWorkThread.start();
        mAppWorkHandler = new Handler(mAppWorkThread.getLooper());
        sqLiteOpenHelper = new PackageSqlHelper(mContext);
    }

    public void addMessage(String pkgName, String msg) {
        if (messageInfos == null) {
            messageInfos = new HashMap<>();
        }
        messageInfos.put(pkgName,msg);
        mContext.sendBroadcast(new Intent(ACTION_UPADTE_MESSAGE));
    }

    public void removeMessage(String pkg){
        messageInfos.remove(pkg);
    }

    public Map<String, String> getMessageInfos() {
        return messageInfos;
    }

    public void insert(final ArrayList<AppInfo> infos) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
                db.beginTransaction();
                SQLiteStatement stat = db.compileStatement(IAppInfoTable.INSERT_OR_IGNORE_SQL);
                for (AppInfo info : infos) {
                    stat.clearBindings();
                    stat.bindString(1, info.getApp_name());
                    stat.bindString(2, info.getPkgName());
                    stat.bindLong(3, info.getVerCode());
                    stat.bindString(4, info.getVerName());
                    stat.bindLong(5, info.isListen() ? 1 : 0);
                    stat.executeInsert();
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
            }
        };
        mAppWorkHandler.post(runnable);
    }

    private void insert(final AppInfo info) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
                db.beginTransaction();

                SQLiteStatement stat = db.compileStatement(IAppInfoTable.INSERT_OR_IGNORE_SQL);
                stat.clearBindings();
                stat.bindString(1, info.getApp_name());
                stat.bindString(2, info.getPkgName());
                stat.bindLong(3, info.getVerCode());
                stat.bindString(4, info.getVerName());
                stat.bindLong(5, info.isListen() ? 1 : 0);
                stat.executeInsert();

                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
            }
        };
        mAppWorkHandler.post(runnable);
    }

    public void update(final String pkg, final boolean needListen) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(IAppInfoTable.NEEDNOTIFICATIONLISTEN, needListen ? 1 : 0);
                db.update(IAppInfoTable.TABLE_NAME, contentValues, IAppInfoTable.PKGNAME + " = ?", new String[]{pkg});
                mContext.sendBroadcast(new Intent(ACTION_UPADTE_APP_DATA));
            }
        };
        mAppWorkHandler.post(runnable);
    }

    private void delete(String packageName) {

    }

    public static AppInfo parseAppInfoBeanFromCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        AppInfo info = new AppInfo();
        info.setApp_name(cursor.getString(cursor.getColumnIndex(IAppInfoTable.APPNAME)));
        info.setPkgName(cursor.getString(cursor.getColumnIndex(IAppInfoTable.PKGNAME)));
        info.setListen(cursor.getInt(cursor.getColumnIndex(IAppInfoTable.NEEDNOTIFICATIONLISTEN)) == 1);
        info.setVerCode(cursor.getInt(cursor.getColumnIndex(IAppInfoTable.VERSIONCODE)));
        info.setVerName(cursor.getString(cursor.getColumnIndex(IAppInfoTable.VERSIONNAME)));
        return info;
    }

    public ArrayList<AppInfo> getAppInfosFromDb() {
        final ArrayList<AppInfo> appInfos = new ArrayList<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (sqLiteOpenHelper != null & mContext != null) {

                    SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
                    Cursor cursor = db.query(IAppInfoTable.TABLE_NAME, null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        do {
                            AppInfo appInfo = parseAppInfoBeanFromCursor(cursor);
                            if (appInfo != null) {
                                appInfos.add(appInfo);
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    for (AppInfo appInfo : appInfos) {
                        if (appInfo.isListen()) {
                            packageNameList.add(appInfo.getPkgName());
                        }
                    }
                    mContext.sendBroadcast(new Intent(NotificationListActivity.ACTION_GETDATA_FROM_DB));
                }
            }
        };
        mAppWorkHandler.post(runnable);
        return appInfos;
    }

    public boolean needListen(String pkgName) {
        if (packageNameList.contains(pkgName)) {
            return true;
        }
        return false;
    }

    private void destroy() {
        if (mAppWorkHandler != null) {
            mAppWorkHandler.removeCallbacksAndMessages(null);
            mAppWorkHandler = null;
        }
        if (mAppWorkThread != null) {
            mAppWorkThread.quit();
            mAppWorkThread = null;
        }
        if (sqLiteOpenHelper != null) {
            sqLiteOpenHelper.close();
        }
        mContext = null;
        myNotificationManager = null;
    }
}
