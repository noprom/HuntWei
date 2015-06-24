package com.huntdreams.wei.cache.database.tables;

/**
 * 用户个人信息表
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class UsersTable {
    public static final String NAME = "users";

    public static final String UID = "uid";
    public static final String TIMESTAMP = "timestamp";
    public static final String JSON = "json";
    public static final String CREATE = "create table " + NAME
            + "("
            + UID + " integer primary key autoincrement,"
            + TIMESTAMP + " integer,"
            + JSON + " text"
            + ");";
}
