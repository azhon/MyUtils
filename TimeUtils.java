package com.hjtech.baselib.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 文件名:    TimeUtils
 * 创建者:    阿钟
 * 创建时间:  2017/8/22 on 15:12
 * 描述:     TODO 时间格式化工具类
 */
public class TimeUtils {

    /**
     * 将时间戳转换为时间
     *
     * @param time 时间戳
     * @return yyyy-MM-dd
     */
    public static String yyyyMMdd(long time) {
        if (time == 0)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        return format.format(date);
    }

    /***
     * 时间戳转换为时间
     *
     * @param time 时间戳
     * @return HH:mm:ss
     */
    public static String HHmmss(long time) {
        if (time == 0)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(time);
        return format.format(date);
    }

    /***
     * 时间戳转换为时间
     *
     * @param time 时间戳
     * @return yyyy-MM-dd HH:mm
     */
    public static String yyyyMMddHHmm(long time) {
        if (time == 0)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(time);
        return format.format(date);
    }

    /***
     * 时间戳转换为时间
     *
     * @param time 时间戳
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String yyyyMMddHHmmss(long time) {
        if (time == 0)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return format.format(date);
    }

    /***
     * 时间戳转换为时间
     *
     * @param time 时间戳
     * @return MM-dd HH:mm
     */
    public static String MMddHHmm(long time) {
        if (time == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        Date date = new Date(time);
        return format.format(date);
    }

    /***
     * 时间戳转换为时间
     *
     * @param time 时间戳
     * @return MM-dd HH:mm:ss
     */
    public static String MMddHHmmss(long time) {
        if (time == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        Date date = new Date(time);
        return format.format(date);
    }

    /***
     * 时间转换为时间戳
     *
     * @param time 时间
     * @param format 格式 例如：yyyy-MM-dd HH:mm:ss
     * @return 时间戳
     */
    public static long toTimeStamp(String time, String format) {
        SimpleDateFormat format = new SimpleDateFormat(format);
        return format.parse(time).getTime();
    }
}
