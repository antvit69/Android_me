package com.example.android.android_me.log;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by antlap on 22/07/2017.
 */

public class CustomTree  extends Timber.DebugTree{
    private String logSuffix = " <antlap>";
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        String logMessage = message + logSuffix;
        switch (priority) {
            case Log.VERBOSE:
                Log.println(priority, tag, logMessage);
                break;
            case Log.DEBUG:
                Log.println(priority, tag, logMessage);
                break;
            case Log.INFO:
                Log.println(priority, tag, logMessage);
                break;
            case Log.WARN:
                Log.println(priority, tag, logMessage);
                break;
            case Log.ERROR:
                Log.println(priority, tag, logMessage);
                break;
            case Log.ASSERT:
                Log.println(priority, tag, logMessage);
                break;
        }
    }
}
