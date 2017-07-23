package com.example.android.android_me.log;

import com.example.android.android_me.BuildConfig;

/**
 * Created by antlap on 23/07/2017.
 */

public class Logbook {
    public static final String TIMBER = " <antlap>";

    public static class Log {
        public static void v(String tag, String msg, Object... args){
            if (BuildConfig.DEBUG) {
                android.util.Log.v(tag, formatMessage(msg, args));
            }
        }
        public static void v(String tag, String msg, Throwable throwable, Object... args){
            if (BuildConfig.DEBUG) {
                android.util.Log.v(tag, formatMessage(msg, args), throwable);
            }
        }
        public static void d(String tag, String msg, Object... args){
            if (BuildConfig.DEBUG) {
                android.util.Log.d(tag, formatMessage(msg, args));
            }
        }
        public static void d(String tag, String msg, Throwable throwable, Object... args){
            if (BuildConfig.DEBUG) {
                android.util.Log.d(tag, formatMessage(msg, args), throwable);
            }
        }
        public static void i(String tag, String msg, Object... args){
            if (BuildConfig.DEBUG) {
                android.util.Log.i(tag, formatMessage(msg, args));
            }
        }
        public static void i(String tag, String msg, Throwable throwable, Object... args){
            if (BuildConfig.DEBUG) {
                android.util.Log.i(tag, formatMessage(msg, args), throwable);
            }
        }
        public static void w(String tag, String msg, Object... args){
            android.util.Log.w(tag, formatMessage(msg, args));
        }
        public static void w(String tag, Throwable throwable){
            android.util.Log.w(tag, throwable);
        }
        public static void w(String tag, String msg, Throwable throwable, Object... args){
            android.util.Log.w(tag, formatMessage(msg, args), throwable);
        }
        public static void e(String tag, String msg, Object... args){
            android.util.Log.e(tag, formatMessage(msg, args));
        }
        public static void e(String tag, Throwable throwable){
            android.util.Log.e(tag, formatMessage(null), throwable);
        }
        public static void e(String tag, String msg, Throwable throwable, Object... args){
            android.util.Log.e(tag, formatMessage(msg, args), throwable);
        }
        public static void wtf(String tag, String msg, Object... args){
            android.util.Log.wtf(tag, formatMessage(msg, args));
        }
        public static void wtf(String tag, Throwable throwable){
            android.util.Log.wtf(tag, throwable);
        }
        public static void wtf(String tag, String msg, Throwable throwable, Object... args){
            android.util.Log.wtf(tag, formatMessage(msg, args), throwable);
        }

        /* eg: String output = formatMessage("My name is: %s, age: %d", "Joe", 35);
            => output = "My name is: Joe, age: 35"
           see: https://dzone.com/articles/java-string-format-examples
                https://docs.oracle.com/javase/8/docs/api/index.html
         */
        private static String formatMessage(String msg, Object... args) {
            String message = msg;
            if(message == null){
                message = "";
            } else if(args.length > 0) {
                message = String.format(message, args);
            }
            return message + TIMBER;
        }

    }
}
