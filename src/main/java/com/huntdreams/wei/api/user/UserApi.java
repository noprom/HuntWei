package com.huntdreams.wei.api.user;

import android.util.Log;

import com.google.gson.Gson;
import com.huntdreams.wei.api.BaseApi;
import com.huntdreams.wei.api.Constants;
import com.huntdreams.wei.model.UserModel;
import com.sina.weibo.sdk.net.WeiboParameters;

import org.json.JSONException;
import org.json.JSONObject;

import static com.huntdreams.wei.BuildConfig.DEBUG;
/**
 * 用户个人信息API接口
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class UserApi extends BaseApi{

    private static final String TAG = UserApi.class.getSimpleName();

    public static UserModel getUser(String uid){
        WeiboParameters params = new WeiboParameters();
        params.put("uid", uid);

        try{
            JSONObject json = request(Constants.USER_SHOW, params, HTTP_GET);
            UserModel user = new Gson().fromJson(json.toString(), UserModel.class);
            return user;
        } catch (JSONException e) {
            if(DEBUG){
                Log.e(TAG, "Failed to fetch user info from net: " + e.getClass().getSimpleName());
            }
            return null;
        }
    }
}
