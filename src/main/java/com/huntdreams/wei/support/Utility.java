package com.huntdreams.wei.support;

import java.util.concurrent.TimeUnit;

/**
 * 自定义工具类
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class Utility {

    /**
     * 返回即将多少天后过期
     * @param time
     * @return
     */
    public static int expireTimeInDays(long time){
        return (int) TimeUnit.MILLISECONDS.toDays(time - System.currentTimeMillis());
    }

    /**
     * 判断Token是否过期
     * @param time
     * @return
     */
    public static boolean isTokenExpired(long time){
        return time <= System.currentTimeMillis();
    }

    /**
     * 检查缓存文件是否过期
     * @param createtime
     * @param availableDays
     * @return
     */
    public static boolean isCacheAvailable(long createtime, int availableDays){
        return System.currentTimeMillis() <= createtime + TimeUnit.DAYS.toMillis(availableDays);
    }
}
