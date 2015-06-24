package com.huntdreams.wei.cache.file;

import android.content.Context;

import com.huntdreams.wei.cache.Constants;
import com.huntdreams.wei.support.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * 文件缓存类
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class FileCacheManager {

    private static FileCacheManager mInstance;
    private File mCacheDir;

    private FileCacheManager(Context context){
        mCacheDir = context.getExternalCacheDir();
    }

    public static synchronized FileCacheManager instance(Context context){
        if(mInstance == null){
            mInstance = new FileCacheManager(context);
        }
        return mInstance;
    }

    public void createCache(String type, String name, byte[] data) throws IOException{
       String path = mCacheDir.getPath() + "/" + type + "/" + name;
       File f = new File(path);
       if(f.exists()){
           f.delete();
       }
       f.getParentFile().mkdirs();
       f.createNewFile();

       FileOutputStream out = new FileOutputStream(path);
       out.write(data);
       out.close();
    }

    public byte[] createCacheFromNetwork(String type, String name, String url) throws IOException{
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        byte[] buf = readInputStream(conn.getInputStream());
        createCache(type, name, buf);
        return buf;
    }

    public byte[] getCache(String type, String name) throws IOException{
        String path = mCacheDir.getPath() + "/" + type + "/" + name;
        File f = new File(path);
        if(!f.exists()){
            return null;
        }else{
            FileInputStream in = new FileInputStream(path);
            return readInputStream(in);
        }
    }

    private byte[] readInputStream(InputStream in) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = in.read(buf))!= -1){
            out.write(buf, 0, len);
        }
        in.close();
        return out.toByteArray();
    }

    public void clearUnavailable(){
        clearUnavailable(mCacheDir);
    }

    private void clearUnavailable(File dir){
        File[] files = dir.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                clearUnavailable(f);
            }else{
                long time = f.lastModified();
                if(!Utility.isCacheAvailable(TimeUnit.SECONDS.toMillis(time), Constants.FILE_CACHE_DAYS)){
                    f.delete();
                }
            }
        }
    }

}
