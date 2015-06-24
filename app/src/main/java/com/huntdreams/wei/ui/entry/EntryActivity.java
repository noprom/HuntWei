package com.huntdreams.wei.ui.entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.huntdreams.wei.cache.file.FileCacheManager;
import com.huntdreams.wei.cache.login.LoginApiCache;
import com.huntdreams.wei.support.Utility;
import com.huntdreams.wei.ui.login.LoginActivity;
import com.huntdreams.wei.ui.main.MainActivity;

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

        // Clean cache
        FileCacheManager.instance(this).clearUnavailable();

        LoginApiCache login = new LoginApiCache(this);
        if(needsLogin(login)){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_MAIN);
            i.setClass(this, LoginActivity.class);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent();
            i.setAction(Intent.ACTION_MAIN);
            i.setClass(this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    private boolean needsLogin(LoginApiCache login){
        // TODO consider expire date
        return login.getAccessToken() == null || Utility.isTokenExpired(login.getExpireDate());
    }

}
