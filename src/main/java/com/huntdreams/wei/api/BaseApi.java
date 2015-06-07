package com.huntdreams.wei.api;


import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.WeiboParameters;

import org.json.JSONException;
import org.json.JSONObject;

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

    protected static JSONObject request(String url, WeiboParameters params, String method)throws JSONException {
        if(mAccessToken == null){
            return null;
        }else{
            params.put("access_token", mAccessToken);
            String jsonData = AsyncWeiboRunner
        }
    }
}
