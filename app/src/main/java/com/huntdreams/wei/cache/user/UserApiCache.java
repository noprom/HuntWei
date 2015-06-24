package com.huntdreams.wei.cache.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.huntdreams.wei.api.user.UserApi;
import com.huntdreams.wei.cache.Constants;
import com.huntdreams.wei.cache.database.DataBaseHelper;
import com.huntdreams.wei.cache.database.tables.UsersTable;
import com.huntdreams.wei.cache.file.FileCacheManager;
import com.huntdreams.wei.model.UserModel;

import java.io.IOException;

/**
 * 用户信息缓存类
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class UserApiCache {

    private DataBaseHelper mHelper;
    private FileCacheManager mManager;

    public UserApiCache(Context context){
        mHelper = DataBaseHelper.instance(context);
        mManager = FileCacheManager.instance(context);
    }

    public UserModel getUser(String uid){
        Cursor cursor = mHelper.getReadableDatabase().query(UsersTable.NAME, new String[]{
                UsersTable.UID,
                UsersTable.TIMESTAMP,
                UsersTable.JSON
        },UsersTable.UID + "=?", new String[]{uid}, null, null, null);
        UserModel model;

        if(cursor.getCount() >=1){
            cursor.moveToFirst();
            model = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(UsersTable.JSON)), UserModel.class);
            model.timestamp = cursor.getInt(cursor.getColumnIndex(UsersTable.TIMESTAMP));
        }else{
            model = UserApi.getUser(uid);
            if(model == null){
                return null;
            }

            // Insert into database
            ContentValues values = new ContentValues();
            values.put(UsersTable.UID, uid);
            values.put(UsersTable.TIMESTAMP, model.timestamp);
            values.put(UsersTable.JSON, new Gson().toJson(model));

            SQLiteDatabase db = mHelper.getWritableDatabase();
            db.insert(UsersTable.NAME, null, values);
        }
        return model;
    }

    public Bitmap getSmallAvatar(UserModel model){
        byte[] cache;
        try{
            cache = mManager.getCache(Constants.FILE_CACHE_AVATAR_SMALL, model.id);
        } catch (IOException e) {
            cache = null;
        }

        if(cache == null){
            try{
                cache = mManager.createCacheFromNetwork(Constants.FILE_CACHE_AVATAR_SMALL, model.id, model.profile_image_url);
            } catch (IOException e) {
                cache = null;
            }
        }

        return cache != null ? BitmapFactory.decodeByteArray(cache, 0, cache.length) : null;
    }
}
