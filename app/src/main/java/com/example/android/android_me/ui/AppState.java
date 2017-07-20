package com.example.android.android_me.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by antlap on 20/07/2017.
 */

public class AppState implements BodyPartFragment.PartChangeListener {
    public static final String ANDROIDME_PREFERENCES = "AndroidMe";
    public static final String HEAD_INDEX_LABEL = "headIndex";
    public static final String BODY_INDEX_LABEL = "bodyIndex";
    public static final String LEG_INDEX_LABEL = "legIndex";
    public static final int HEAD_PART_ID = 0;
    public static final int BODY_PART_ID = 1;
    public static final int LEG_PART_ID = 2;

    private static final AppState sInstance = new AppState();
    private static final String TAG =  "antlap_" + AppState.class.getSimpleName();

    private int headIndex;
    private int bodyIndex;
    private int legIndex;
    private Context context;

    private AppState() {
    }

    public static AppState getInstance() {
        return sInstance;
    }

    public void setContext(Context context){
        boolean isFirstTime = context == null;
        sInstance.context = context;
        if(isFirstTime){
            loadSharedPreferences();
        }
    }

    public void persistState(){
        sInstance.saveSharedPreferences();
    }

    public void setIndexes(int headIndex, int bodyIndex, int legIndex){
        this.headIndex = headIndex;
        this.bodyIndex = bodyIndex;
        this.legIndex = legIndex;
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
            throw new IllegalMonitorStateException("Context undefined, use setContext");
        }
        return prefs;
    }

    private void loadSharedPreferences() {
        Log.d(TAG, "loading state from SharedPreferences");
        SharedPreferences prefs = getSharedPreferences();
        headIndex = prefs.getInt(HEAD_INDEX_LABEL, 0);
        bodyIndex = prefs.getInt(BODY_INDEX_LABEL, 0);
        legIndex = prefs.getInt(LEG_INDEX_LABEL, 0);
    }

    private void saveSharedPreferences() {
        Log.d(TAG, "saveSharedPreferences: headIndex = " + headIndex + ", bodyIndex = " + bodyIndex + ", legIndex = " + legIndex);
        SharedPreferences prefs = getSharedPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HEAD_INDEX_LABEL, headIndex);
        editor.putInt(BODY_INDEX_LABEL, bodyIndex);
        editor.putInt(LEG_INDEX_LABEL, legIndex);
        editor.commit();
    }

    @Override
    public void newIndex(int index, int partCode) {
        Log.d(TAG, "Body part changed: index=" + index + " - partCode = " + partCode);
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
