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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huntdreams.wei.R;
import com.huntdreams.wei.cache.login.LoginApiCache;
import com.huntdreams.wei.cache.user.UserApiCache;
import com.huntdreams.wei.model.UserModel;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    // Drawer content
    private TextView mName;
    private ImageView mAvatar;
    private ListView mMy;
    private ListView mAtMe;

    // Model and cache
    private LoginApiCache mLoginCache;
    private UserApiCache mUserCache;
    private UserModel mUser;

    // Temp fields
    private TextView mLastChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize navigation drawer
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.drawable.ic_drawer, 0, 0){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(mLastChoice == null){
                    mLastChoice = (TextView) mMy.getChildAt(0);
                    mLastChoice.getPaint().setFakeBoldText(true); // 文字加粗
                    mLastChoice.invalidate();
                }
            }
        };
        mDrawer.setDrawerListener(mToggle);
        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        // Init listview
        mMy = (ListView) findViewById(R.id.list_my);
        mAtMe = (ListView) findViewById(R.id.list_at_me);
        mMy.setVerticalScrollBarEnabled(false);
        mMy.setChoiceMode(ListView.CHOICE_MODE_NONE);
        mAtMe.setVerticalScrollBarEnabled(false);
        mAtMe.setChoiceMode(ListView.CHOICE_MODE_NONE);
        mMy.setAdapter(new ArrayAdapter(this, R.layout.main_drawer_item, getResources().getStringArray(R.array.my_array)));
        mAtMe.setAdapter(new ArrayAdapter(this, R.layout.main_drawer_item, getResources().getStringArray(R.array.at_me_array)));

        // Init listview click events
        mMy.setOnItemClickListener(this);
        mAtMe.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mLastChoice != null){
            mLastChoice.getPaint().setFakeBoldText(false);
            mLastChoice.invalidate();
        }

        if(parent == mMy){
            TextView tv = (TextView) view;
            tv.getPaint().setFakeBoldText(true);
            tv.invalidate();
            mLastChoice = tv;
            // TODO switch fragments
        }else if(parent == mAtMe){
            TextView tv = (TextView) view;
            tv.getPaint().setFakeBoldText(true);
            tv.invalidate();
            mLastChoice = tv;
            // TODO Switch fragments
        }
        // Close
        mDrawer.closeDrawer(Gravity.START);
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
