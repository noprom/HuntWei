package com.huntdreams.wei.cache.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.huntdreams.wei.api.BaseApi;
import com.huntdreams.wei.api.login.LoginApi;
import com.huntdreams.wei.api.user.AccountApi;

import static com.huntdreams.wei.BuildConfig.DEBUG;
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
    private String mUid;
    private long mExpireDate;
    private String mAppId;
    private String mAppSecret;

    public LoginApiCache(Context context){
        mPrefs = context.getSharedPreferences("access_token",Context.MODE_PRIVATE);
        mAccessToken = mPrefs.getString("access_token", null);
        mUid = mPrefs.getString("uid", null);
        mExpireDate = mPrefs.getLong("expires_in", Long.MIN_VALUE);
        mAppId = mPrefs.getString("app_id", null);
        mAppSecret = mPrefs.getString("app_secret", null);

        if(mAccessToken != null){
            BaseApi.setmAccessToken(mAccessToken);
        }
    }

    public void login(String appId, String appSecret, String username,String password){
        if(mAccessToken == null || mExpireDate == Long.MIN_VALUE){
            if(DEBUG){
                Log.d(TAG, "access token not initialized, running login function");
            }

            String result[] = LoginApi.login(appId, appSecret, username, password);
            if(result != null){
                if(DEBUG){
                    Log.d(TAG, "result got, loading to cache");
                }
                mAccessToken = result[0];
                BaseApi.setmAccessToken(mAccessToken);
                mUid = AccountApi.getUid();
                mExpireDate = System.currentTimeMillis() + Long.valueOf(result[1]);
                mAppId = appId;
                mAppSecret = appSecret;
            }

        }
    }

    public void logout(){
        mAccessToken = null;
        mExpireDate = Long.MIN_VALUE;
        mPrefs.edit().remove("access_token").remove("expires_in").remove("uid").commit();
    }

    public void cache(){
        mPrefs.edit().putString("access_token", mAccessToken)
                .putLong("expires_in", mExpireDate)
                .putString("uid", mUid)
                .putString("app_id", mAppId)
                .putString("app_secret", mAppSecret)
                .commit();
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public String getUid() {
        return mUid;
    }

    public long getExpireDate() {
        return mExpireDate;
    }

    public String getAppId() {
        return mAppId;
    }

    public String getAppSecret() {
        return mAppSecret;
    }
}
