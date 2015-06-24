package com.huntdreams.wei.cache.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.huntdreams.wei.cache.database.tables.UsersTable;

/**
 * 自定义数据库帮助类
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class DataBaseHelper extends SQLiteOpenHelper{

    private static String DB_NAME = "weibo_data";
    private static int DB_VER = 1;
    private static DataBaseHelper instance;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsersTable.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // TODO Clean unavailable records

    public static synchronized DataBaseHelper instance(Context context){
        if(instance == null){
            instance = new DataBaseHelper(context);
        }
        return instance;
    }
}
