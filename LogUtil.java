package com.zsy.lambda;

/*
 * 文件名:     LogUtil
 * 创建者:     阿钟
 * 创建时间:   2016/10/22 20:21
 * 描述:       输出Log的工具类
 */

import android.util.Log;

public class LogUtil {

    //Log开关
    private static boolean isOpen = true;

    /**
     * 设置是否输出Log
     *
     * @param b
     */
    public static void setSwitch(boolean b) {
        isOpen = b;
    }

    //**********************Debug***********//
    public static void d(String tag, String msg) {
        if (isOpen) Log.d(tag, msg);
    }

    public static void d(String tag, int msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, float msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, long msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, double msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, boolean msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    //**********************Info***********//
    public static void i(String tag, String msg) {
        if (isOpen) Log.i(tag, msg);
    }

    public static void i(String tag, int msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, float msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }


    public static void i(String tag, long msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, double msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, boolean msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    //**********************Error***********//
    public static void e(String tag, String msg) {
        if (isOpen) Log.e(tag, msg);
    }

    public static void e(String tag, int msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, float msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, Long msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, double msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, boolean msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

}
