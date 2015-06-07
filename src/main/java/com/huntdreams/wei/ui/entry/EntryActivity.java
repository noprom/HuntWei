package com.huntdreams.wei.ui.entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.huntdreams.wei.api.BaseApi;
import com.huntdreams.wei.cache.login.LoginApiCache;
import com.huntdreams.wei.support.Utility;
import com.huntdreams.wei.ui.login.LoginActivity;

/**
 * 应用程序入口
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class EntryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginApiCache login = new LoginApiCache(this);
        if(needsLogin(login)){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_MAIN);
            i.setClass(this, LoginActivity.class);
            startActivity(i);
            finish();
        }else{
            BaseApi.setmAccessToken(login.getAccessToken());
            // TODO Enter the main time line.
        }

    }

    private boolean needsLogin(LoginApiCache login){
        // TODO consider expire date
        return login.getAccessToken() == null || Utility.isTokenExpired(login.getExpireDate());
    }

}
