package com.huntdreams.wei.ui.main;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.huntdreams.wei.R;
import com.huntdreams.wei.cache.login.LoginApiCache;
import com.huntdreams.wei.cache.user.UserApiCache;
import com.huntdreams.wei.model.UserModel;

public class MainActivity extends Activity {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    // Drawer content
    private TextView mName;
    private ImageView mAvatar;

    // Model and cache
    private LoginApiCache mLoginCache;
    private UserApiCache mUserCache;
    private UserModel mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize navigation drawer
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.drawable.ic_drawer, 0, 0);
        mDrawer.setDrawerListener(mToggle);
        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        // Init my account
        mName = (TextView) findViewById(R.id.my_name);
        mName.getPaint().setFakeBoldText(true);
        mAvatar = (ImageView) findViewById(R.id.my_avatar);
        mLoginCache = new LoginApiCache(this);
        mUserCache = new UserApiCache(this);
        new InitializeTask().execute();

        // Init ActionBar style
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            return mToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private class InitializeTask extends AsyncTask<Void, Object, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            // Username
            mUser = mUserCache.getUser(mLoginCache.getUid());
            publishProgress(new Object[]{0});

            // My avatar
            Bitmap avatar = mUserCache.getSmallAvatar(mUser);
            if(avatar != null){
                publishProgress(new Object[]{1, avatar});
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            int value = (int) values[0];
            switch (value){
                case 0:
                    // Show username
                    mName.setText(mUser.screen_name);
                    break;
                case 1:
                    // Show avatar
                    mAvatar.setImageBitmap((Bitmap) values[1]);
                    break;
            }
            super.onProgressUpdate(values);
        }
    }
}
