package com.huntdreams.wei.api;


import android.util.Log;

import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.WeiboParameters;

import org.json.JSONException;
import org.json.JSONObject;

import static com.huntdreams.wei.BuildConfig.DEBUG;

/**
 * Api基类
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 *          Created by noprom on 2015/6/7.
 */
public class BaseApi {
    private static final String TAG = BaseApi.class.getSimpleName();

    // Http methods
    protected static final String HTTP_GET = "GET";
    protected static final String HTTP_POST = "POST";

    // Access token
    private static String mAccessToken;

    protected static JSONObject request(String url, WeiboParameters params, String method) throws JSONException {
        if (mAccessToken == null) {
            return null;
        } else {
            params.put("access_token", mAccessToken);
            String jsonData = AsyncWeiboRunner.request(url, params, method);

            if (DEBUG) {
                Log.d(TAG, "jsonData = " + jsonData);
            }

            if (jsonData != null && jsonData.contains("{")) {
                return new JSONObject(jsonData);
            } else {
                return null;
            }
        }
    }

    protected static JSONObject requestWithoutAccessToken(String url, WeiboParameters params, String method) throws JSONException {
        String jsonData = AsyncWeiboRunner.request(url, params, method);

        if (DEBUG) {
            Log.d(TAG, "jsonData = " + jsonData);
        }

        if (jsonData != null && jsonData.contains("{")) {
            return new JSONObject(jsonData);
        } else {
            return null;
        }
    }

    public static String getmAccessToken() {
        return mAccessToken;
    }

    public static void setmAccessToken(String mAccessToken) {
        BaseApi.mAccessToken = mAccessToken;
    }
}
