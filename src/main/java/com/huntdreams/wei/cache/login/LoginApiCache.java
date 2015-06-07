package com.huntdreams.wei.cache.login;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 登陆缓存
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class LoginApiCache {

    private static final String TAG = LoginApiCache.class.getSimpleName();

    private SharedPreferences mPrefs;
    private String mAccessToken;
    private long mExpireDate;
    private String mAppId;
    private String mAppSecret;

    public LoginApiCache(Context context){
        mPrefs = context.getSharedPreferences("access_token",Context.MODE_PRIVATE);
        mAccessToken = mPrefs.getString("access_token" ,null);
        mExpireDate = mPrefs.getLong("expires_in", Long.MIN_VALUE);
        mAppId = mPrefs.getString("app_id", null);
        mAppSecret = mPrefs.getString("app_secret", null);
    }


}
