package com.training.storage.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.training.storage.utils.SQLUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by chenqiuyi on 16/10/26.
 */

public class MyDatabaseOpenHelp extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mysql.db";

    public MyDatabaseOpenHelp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLUtils.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return FromSd();
//        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
//        return super.getReadableDatabase();
        return FromSd();
    }

    private SQLiteDatabase FromSd() {
        if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
            String path = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/database";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            File dbf = new File(path + "/" + DATABASE_NAME);
            //数据库文件是否创建成功
            boolean isFileCreateSuccess = false;
            if (!dbf.exists()) {
                try {
                    isFileCreateSuccess = dbf.createNewFile();
                } catch (IOException ex) {

                }
            } else {
                isFileCreateSuccess = true;
            }
            if (isFileCreateSuccess) {
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
//                db.execSQL(SQLUtils.SQL_CREATE_ENTRIES);
                return db;
            }
        }
        return null;
    }
}
