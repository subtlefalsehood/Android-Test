package com.training.notification;

/**
 * Created by chenqiuyi on 17/3/14.
 */

public interface IAppInfoTable {
    public final static String TABLE_NAME = "appinfo";

    public final static String ID = "id";
    public final static String APPNAME = "app_name";
    public final static String PKGNAME = "pkg_name";
    public final static String VERSIONCODE = "version_code";
    public final static String VERSIONNAME = "version_name";
    public final static String NEEDNOTIFICATIONLISTEN = "need_notification_listen";

    public final static String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + APPNAME + " TEXT,"
            + PKGNAME + " TEXT,"
            + VERSIONCODE + " INTEGER,"
            + VERSIONNAME + " TEXT,"
            + NEEDNOTIFICATIONLISTEN + " INTEGER"
            + ")";

    public final static String INSERT_OR_IGNORE_SQL = "insert or ignore into " + IAppInfoTable.TABLE_NAME + "("
            + IAppInfoTable.APPNAME
            + "," + IAppInfoTable.PKGNAME
            + "," + IAppInfoTable.VERSIONCODE
            + "," + IAppInfoTable.VERSIONNAME
            + "," + IAppInfoTable.NEEDNOTIFICATIONLISTEN
            + ")"+ " values(?,?,?,?,?)";

    public final static String CREATE_UNIQUE_INDEX_SQL = "CREATE UNIQUE INDEX unique_index_id ON " + TABLE_NAME + " (" + PKGNAME + ")";

    public final static String DROP_SQL = "drop table if exists " + TABLE_NAME;
}
