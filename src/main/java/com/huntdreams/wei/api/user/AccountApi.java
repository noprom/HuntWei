package com.huntdreams.wei.api.user;

import com.huntdreams.wei.api.BaseApi;
import com.huntdreams.wei.api.Constants;
import com.sina.weibo.sdk.net.WeiboParameters;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户账户API接口
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class AccountApi extends BaseApi{

    public static String getUid(){
        try{
            JSONObject json = request(Constants.GET_UID, new WeiboParameters(), HTTP_GET);
            return json.optString("uid");
        } catch (JSONException e) {
            return null;
        }
    }
}
