package com.example.android.android_me.general;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.android_me.BuildConfig;
import com.example.android.android_me.log.CustomTree;
import com.example.android.android_me.log.Logbook.Log;
import com.example.android.android_me.ui.BodyPartFragment;

import timber.log.Timber;

/**
 * Created by antlap on 20/07/2017.
 */

public class AppState extends Application implements BodyPartFragment.PartChangeListener {
    private static final String TAG = AppState.class.getSimpleName();
    private static final AppState sInstance = new AppState();

    public static final String ANDROIDME_PREFERENCES = "AndroidMe";
    public static final String HEAD_INDEX_LABEL = "headIndex";
    public static final String BODY_INDEX_LABEL = "bodyIndex";
    public static final String LEG_INDEX_LABEL = "legIndex";
    public static final int HEAD_PART_ID = 0;
    public static final int BODY_PART_ID = 1;
    public static final int LEG_PART_ID = 2;

    private int headIndex;
    private int bodyIndex;
    private int legIndex;
    private Context context;

    private AppState() {
    }

    public static AppState getInstance() {
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "getInstance: waiting for setContext call.");
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(new CustomTree());
        }

        testLoggers();

        return sInstance;
    }

    private static void testLoggers() {
        Timber.v("Timber log test 123-%s", 45);
        Timber.d("Timber log test 123-%s", 67);
        Timber.i("Timber log test 123-%s", 89);
        Timber.w("Timber log test 123-%s", 90);
        Timber.e("Timber log test 123-%s", 12);
        Timber.wtf("Timber log test 123-%s", 34);

        Log.v(TAG, "Logbook log test 123-%s-%s", 4, 5);
        Log.d(TAG, "Logbook log test 123-%s-%s", 6, 7);
        Log.i(TAG, "Logbook log test 123-%s-%s", 8, 9);
        Log.w(TAG, "Logbook log test 123-%s-%s", 0, 1);
        Log.e(TAG, "Logbook log test 123-%s-%s", 2, 3);
        Log.wtf(TAG, "Logbook log test 123-%s-%s", 3, 4);
        Log.e(TAG, "Logbook log test 123-%s-%s", new NullPointerException("A my NullPointerException!"), 11, 22);
        Log.e(TAG, new NullPointerException("A my NullPointerException!"));
    }

    public void setContext(Context context){
        Timber.d("setContext: ");
        boolean isFirstTime = sInstance.context == null;
        sInstance.context = context;
        if(isFirstTime){
            loadSharedPreferences();
        }
    }

    public void persistState(){
        sInstance.saveSharedPreferences();
    }

    public void setHeadIndex(int headIndex){
        this.headIndex = headIndex;
    }

    public void setBodyIndex(int bodyIndex){
        this.bodyIndex = bodyIndex;
    }

    public void setLegIndex(int legIndex){
        this.legIndex = legIndex;
    }

    public int getHeadIndex(){
        return headIndex;
    }

    public int getBodyIndex(){
        return bodyIndex;
    }

    public int getLegIndex(){
        return legIndex;
    }


    private SharedPreferences getSharedPreferences() {
        SharedPreferences prefs;
        try {
            prefs = context.getSharedPreferences(ANDROIDME_PREFERENCES, Context.MODE_PRIVATE);
        } catch (NullPointerException exc) {
            throw new IllegalMonitorStateException("Context undefined, must setContext first.");
        }
        return prefs;
    }

    private void loadSharedPreferences() {
        Log.d(TAG, "loading state from SharedPreferences");
        Timber.d("loading state from SharedPreferences");
        SharedPreferences prefs = getSharedPreferences();
        headIndex = prefs.getInt(HEAD_INDEX_LABEL, 0);
        bodyIndex = prefs.getInt(BODY_INDEX_LABEL, 0);
        legIndex = prefs.getInt(LEG_INDEX_LABEL, 0);
    }

    private void saveSharedPreferences() {
        Log.d(TAG, "saveSharedPreferences: headIndex = " + headIndex + ", bodyIndex = " + bodyIndex + ", legIndex = " + legIndex);
        Timber.d("saveSharedPreferences: headIndex = %s, bodyIndex = %s, legIndex = %s", headIndex, bodyIndex, legIndex);
        SharedPreferences prefs = getSharedPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HEAD_INDEX_LABEL, headIndex);
        editor.putInt(BODY_INDEX_LABEL, bodyIndex);
        editor.putInt(LEG_INDEX_LABEL, legIndex);
        editor.commit();
    }

    @Override
    public void newIndex(int index, int partCode) {
        Log.d(TAG, "Body part changed: index = " + index + " - partCode = " + partCode);
        Timber.d("Body part changed: index = %s - partCode = %s", index, partCode);
        switch(partCode){
            case HEAD_PART_ID: //HEAD
                headIndex = index;
                break;
            case BODY_PART_ID: //BODY
                bodyIndex = index;
                break;
            case LEG_PART_ID: //LEG
                legIndex = index;
                break;
        }
    }

}
