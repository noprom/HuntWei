package com.huntdreams.wei.api;

/**
 * API接口常量
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class Constants {

    public static final String SINA_BASE_URL = "https://api.weibo.com/2/";
    // Login
    public static final String OAUTH2_ACCESS_TOKEN = SINA_BASE_URL + "oauth2/access_token";

    // User / Account
    public static final String GET_UID = SINA_BASE_URL + "account/get_uid.json";
    public static final String USER_SHOW = SINA_BASE_URL + "users/show.json";
}
