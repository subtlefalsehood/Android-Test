package com.training.storage.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.training.storage.dao.MyDatabaseOpenHelp;
import com.training.storage.model.SQLiteInfo;

import java.util.List;

/**
 * Created by chenqiuyi on 16/10/26.
 */

public final class SQLUtils {
    // To prevent someone form accidentally instantiating the contract class.
    // give it an empty constructor
    private SQLUtils() {
    }

    public static abstract class MyEntry implements BaseColumns {
        public static final String TABLE_NAME = "myentry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_ENTRY_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
    }

    //    public static final String PATH = "/storage/sdcard0/bddownload";
    public static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY";
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MyEntry.TABLE_NAME
            + " (" + MyEntry._ID + INTEGER_PRIMARY_KEY + COMMA_SEP
            + MyEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP
            + MyEntry.COLUMN_NAME_ENTRY_TITLE + TEXT_TYPE + COMMA_SEP
            + MyEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + ")";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + MyEntry.TABLE_NAME;

    public static long insertData(MyDatabaseOpenHelp help, String content) {
        SQLiteDatabase db = help.getWritableDatabase();
        if (db == null) {
            return -1;
        }
        ContentValues values = new ContentValues();
        values.put(MyEntry.COLUMN_NAME_ENTRY_ID, -1);
        values.put(MyEntry.COLUMN_NAME_ENTRY_TITLE, "无标题");
        values.put(MyEntry.COLUMN_NAME_CONTENT, content);

//        if (db.isOpen()) {
//            db.close();
//        }
        long newRowId = db.insert(MyEntry.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public static int deleteData(MyDatabaseOpenHelp help, String s) {
        SQLiteDatabase db = help.getWritableDatabase();
        if (db == null) {
            return -1;
        }
        int count = db.delete(MyEntry.TABLE_NAME, "content = ?", new String[]{s});
        db.close();
        return count;
    }

    public static boolean searchData(MyDatabaseOpenHelp help, List<SQLiteInfo> sqLiteInfos) {
        SQLiteDatabase db = help.getReadableDatabase();
        if (db == null) {
            return false;
        }
        Cursor cursor_all = db.query(MyEntry.TABLE_NAME, null, null, null, null, null, null);
        for (int i = 0; i < cursor_all.getCount(); i++) {
            cursor_all.moveToPosition(i);
            SQLiteInfo sqLiteInfo = new SQLiteInfo();
            sqLiteInfo.setId(cursor_all.getString(0));
            sqLiteInfo.setEntryid(cursor_all.getString(1));
            sqLiteInfo.setTitle(cursor_all.getString(2));
            sqLiteInfo.setCotent(cursor_all.getString(3));
            sqLiteInfos.add(sqLiteInfo);
        }
        cursor_all.close();
        db.close();
        return true;
//        SQLiteDatabase db = help.getReadableDatabase();
//        if (db == null){
//            return "error";
//        }
//        Cursor cursor_all = db.query(MyEntry.TABLE_NAME, null, null, null, null, null, null);
//        String s = "";
//        if (cursor_all.moveToFirst()) {
//            Logger.d("当前表中的数据条数：" + cursor_all.getCount() + "\n");
//            do {
//                s += "第" + cursor_all.getString(cursor_all.getColumnIndexOrThrow(MyEntry._ID)) + "条\n"
//                        + "ENTRY_ID = " + cursor_all.getString(cursor_all.getColumnIndexOrThrow(MyEntry.COLUMN_NAME_ENTRY_ID)) + "\n"
//                        + "TITLE = " + cursor_all.getString(cursor_all.getColumnIndexOrThrow(MyEntry.COLUMN_NAME_ENTRY_TITLE)) + "\n"
//                        + "CONTENT = " + cursor_all.getString(cursor_all.getColumnIndexOrThrow(MyEntry.COLUMN_NAME_CONTENT))
//                        + "\n********************************************\n";
//            } while (cursor_all.moveToNext());
//        }
//        Logger.d(s);
//        cursor_all.close();
//        Cursor cursor = db.query(MyEntry.TABLE_NAME
//                , null
//                , MyEntry.COLUMN_NAME_CONTENT + "=?"
//                , new String[]{content}, null, null, null);
////        cursor.moveToFirst();
////        return cursor.getColumnIndexOrThrow(MyEntry._ID);
////        if (db.isOpen()) {
////            db.close();
////        }
////        cursor.close();
////        cursor.moveToFirst();
//        if (cursor.getCount() >= 0) {
////            return cursor.getString(cursor.getColumnIndexOrThrow(MyEntry.COLUMN_NAME_CONTENT));
//            return content;
//        } else {
//            return "无";
//        }
    }
}
