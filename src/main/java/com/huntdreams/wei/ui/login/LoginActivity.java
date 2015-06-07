package com.huntdreams.wei.ui.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.huntdreams.wei.R;
import com.huntdreams.wei.api.BaseApi;
import com.huntdreams.wei.cache.login.LoginApiCache;
import com.huntdreams.wei.support.Utility;

import static com.huntdreams.wei.BuildConfig.DEBUG;
/**
 * 登陆界面
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    // Views
    private TextView mAppId;
    private TextView mAppSecret;
    private TextView mUsername;
    private TextView mPassword;
    private MenuItem mMenuItem;

    private LoginApiCache mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Create login instance
        mLogin = new LoginApiCache(this);

        // Init views
        mAppId = (TextView) findViewById(R.id.app_id);
        mAppSecret = (TextView) findViewById(R.id.app_secret);
        mUsername = (TextView) findViewById(R.id.username);
        mPassword = (TextView) findViewById(R.id.passwd);

        // Get cache data
        if(mLogin.getAppId() != null && mLogin.getAppSecret() != null){
            mAppId.setText(mLogin.getAppId());
            mAppSecret.setText(mLogin.getAppSecret());
        }else if(DEBUG){
            mAppId.setText("211160679");
            mAppSecret.setText("63b64d531b98c2dbff2443816f274dd3");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenuItem = menu.add(R.string.login);
        mMenuItem.setShowAsAction(1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item == mMenuItem){
            new LoginTask().execute(new String[]{
                    mAppId.getText().toString(),
                    mAppSecret.getText().toString(),
                    mUsername.getText().toString(),
                    mPassword.getText().toString()

            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LoginTask extends AsyncTask<String, Void, Void>{

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage(getResources().getString(R.string.plz_wait));
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            if(DEBUG){
                Log.d(TAG, "doInBackground...");
            }
            mLogin.login(params[0], params[1], params[2], params[3]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();

            // TODO Use userapi to see if the login is successful
            if(mLogin.getAccessToken() != null){
                if(DEBUG){
                    Log.d(TAG, "Access Token:" + mLogin.getAccessToken());
                    Log.d(TAG, "Expires in:" + mLogin.getExpireDate());
                }

                mLogin.cache();
                BaseApi.setmAccessToken(mLogin.getAccessToken());

                // Alert expire date
                String msg = String.format(getResources().getString(R.string.expires_in), Utility.expireTimeInDays(mLogin.getExpireDate()));
                new AlertDialog.Builder(LoginActivity.this)
                        .setMessage(msg)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // TODO Enter main timeline
                                finish();
                            }
                        })
                        .create().show();
            }else{
                // Wrong username or password
                new AlertDialog.Builder(LoginActivity.this)
                        .setMessage(R.string.login_fail)
                        .setCancelable(true)
                        .create()
                        .show();
            }
        }
    }
}
