/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import static com.example.android.android_me.ui.MainActivity.ANDROIDME_PREFERENCES;
import static com.example.android.android_me.ui.MainActivity.BODY_INDEX_LABEL;
import static com.example.android.android_me.ui.MainActivity.HEAD_INDEX_LABEL;
import static com.example.android.android_me.ui.MainActivity.LEG_INDEX_LABEL;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class AndroidMeActivity extends AppCompatActivity implements BodyPartFragment.PartChangeListener {
    private final String TAG = AndroidMeActivity.class.getSimpleName();

    private int headIndex;
    private int bodyIndex;
    private int legIndex;
    private boolean saveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {
            saveState = true;
            ensureState(getIntent());

            // Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
            // Use setListindex(int index) to set the list index for all BodyPartFragments

            // Create a new head BodyPartFragment
            BodyPartFragment headFragment = new BodyPartFragment();
            headFragment.setChangeListener(this, MainActivity.HEAD_PART_ID);

            // Set the list of image id's for the head fragment and set the position to the second image in the list
            headFragment.setImageIds(AndroidImageAssets.getHeads());

            // Get the correct index to access in the array of head images from the intent
            headFragment.setListIndex(headIndex);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.head_container, headFragment)
                    .commit();

            // Create and display the body and leg BodyPartFragments

            BodyPartFragment bodyFragment = new BodyPartFragment();
            bodyFragment.setChangeListener(this, MainActivity.BODY_PART_ID);
            bodyFragment.setImageIds(AndroidImageAssets.getBodies());
            bodyFragment.setListIndex(bodyIndex);

            fragmentManager.beginTransaction()
                    .add(R.id.body_container, bodyFragment)
                    .commit();

            BodyPartFragment legFragment = new BodyPartFragment();
            legFragment.setChangeListener(this, MainActivity.LEG_PART_ID);
            legFragment.setImageIds(AndroidImageAssets.getLegs());
            legFragment.setListIndex(legIndex);

            fragmentManager.beginTransaction()
                    .add(R.id.leg_container, legFragment)
                    .commit();
        } else {
            saveState = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        close();
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        Log.d(TAG, "onSaveInstanceState: ");
//        outState.putInt(HEAD_INDEX_LABEL, headIndex);
//        outState.putInt(BODY_INDEX_LABEL, bodyIndex);
//        outState.putInt(LEG_INDEX_LABEL, legIndex);
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(TAG, "onStop: ");
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy: ");
//    }

    @Override
    public void newIndex(int index, int partCode) {
        Log.d(TAG, "part index changed: index=" + index + " - partCode = " + partCode);
        switch(partCode){
            case MainActivity.HEAD_PART_ID:
                headIndex = index;
                break;
            case MainActivity.BODY_PART_ID:
                bodyIndex = index;
                break;
            case MainActivity.LEG_PART_ID:
                legIndex = index;
                break;
        }
    }

    public void ensureState(Intent intent) {
        if(intent.hasExtra(MainActivity.HEAD_INDEX_LABEL)
                && intent.hasExtra(MainActivity.BODY_INDEX_LABEL)
                && intent.hasExtra(MainActivity.LEG_INDEX_LABEL)){
            Log.d(TAG, "loading state from Intent: " + intent.getExtras());
            headIndex = intent.getIntExtra(MainActivity.HEAD_INDEX_LABEL, 0);
            bodyIndex = intent.getIntExtra(MainActivity.BODY_INDEX_LABEL, 0);
            legIndex = intent.getIntExtra(MainActivity.LEG_INDEX_LABEL, 0);
        } else {
            loadSharedPreferences();
        }
    }

    private void loadSharedPreferences() {
        Log.d(TAG, "loading state from SharedPreferences");
        SharedPreferences prefs = getSharedPreferences(ANDROIDME_PREFERENCES, Context.MODE_PRIVATE);
        headIndex = prefs.getInt(HEAD_INDEX_LABEL, 0);
        bodyIndex = prefs.getInt(BODY_INDEX_LABEL, 0);
        legIndex = prefs.getInt(LEG_INDEX_LABEL, 0);
    }

    private void saveSharedPreferences() {
        if(saveState) {
            Log.d(TAG, "saveSharedPreferences: headIndex =" + headIndex + ", bodyIndex = " + bodyIndex + ", legIndex = " + legIndex);
            SharedPreferences prefs = getSharedPreferences(ANDROIDME_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(HEAD_INDEX_LABEL, headIndex);
            editor.putInt(BODY_INDEX_LABEL, bodyIndex);
            editor.putInt(LEG_INDEX_LABEL, legIndex);
            editor.commit();
        }
    }

    private void close(){
        Log.d(TAG, "close: ");

        saveSharedPreferences();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(HEAD_INDEX_LABEL, headIndex);
        resultIntent.putExtra(BODY_INDEX_LABEL, bodyIndex);
        resultIntent.putExtra(LEG_INDEX_LABEL, legIndex);
        setResult(RESULT_OK, resultIntent);

        finish();
    }

}
