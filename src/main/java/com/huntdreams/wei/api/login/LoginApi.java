package com.huntdreams.wei.api.login;

import android.util.Log;

import com.huntdreams.wei.api.BaseApi;
import com.huntdreams.wei.api.Constants;
import com.sina.weibo.sdk.net.WeiboParameters;

import org.json.JSONException;
import org.json.JSONObject;

import static com.huntdreams.wei.BuildConfig.DEBUG;
/**
 * 登陆时的API接口
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class LoginApi extends BaseApi{

    private static final String TAG = LoginApi.class.getSimpleName();

    public static String[] login(String appId, String appSecret, String username, String password){
        WeiboParameters params = new WeiboParameters();
        params.put("username", username);
        params.put("password", password);
        params.put("client_id", appId);
        params.put("client_secret", appSecret);
        params.put("grant_type", "password");

        try{
            JSONObject json = requestWithoutAccessToken(Constants.OAUTH2_ACCESS_TOKEN, params, HTTP_POST);
            return new String[]{json.optString("access_token"), json.optString("expires_in")};
        } catch (JSONException e) {
            if(DEBUG){
                Log.d(TAG, "login error:" + e.getClass().getSimpleName());
            }
            return null;
        }
    }
}
