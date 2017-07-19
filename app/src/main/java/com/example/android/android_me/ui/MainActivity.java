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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This activity is responsible for displaying the master list of all images
// Implement the MasterListFragment callback, OnImageClickListener
public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener, BodyPartFragment.PartChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String ANDROIDME_PREFERENCES = "AndroidMe";
    public static final String HEAD_INDEX_LABEL = "headIndex";
    public static final String BODY_INDEX_LABEL = "bodyIndex";
    public static final String LEG_INDEX_LABEL = "legIndex";
    public static final int HEAD_PART_ID = 0;
    public static final int BODY_PART_ID = 1;
    public static final int LEG_PART_ID = 2;

    // Variables to store the values for the list index of the selected images
    // The default value will be index = 0
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    private boolean twoPaneUI;


    // DONE (3) Create a variable to track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ensureState(savedInstanceState);
        loadSharedPreferences();

        // DONE (4) If you are making a two-pane display, add new BodyPartFragments to create an initial Android-Me image
        // Also, for the two-pane display, get rid of the "Next" button in the master list fragment
        twoPaneUI = findViewById(R.id.sw600dp_layout) != null;
        //Log.d(TAG, "onCreate: twoPaneUI = " + twoPaneUI);

        // The "Next" button launches a new AndroidMeActivity
        Button nextButton = (Button) findViewById(R.id.next_button);
        if (twoPaneUI) {
            ViewGroup layout = (ViewGroup) nextButton.getParent();
            if (null != layout) {
                layout.removeView(nextButton);
            }
            showFullBodyParts();
        } else {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
                    Bundle bundle = new Bundle();
                    bundle.putInt("headIndex", headIndex);
                    bundle.putInt("bodyIndex", bodyIndex);
                    bundle.putInt("legIndex", legIndex);
                    Log.d(TAG, "onClick: bundle = " + bundle);
                    Intent intent = new Intent(MainActivity.this, AndroidMeActivity.class);
                    intent.putExtras(bundle);
//                    startActivity(intent);
                    startActivityForResult(intent, 0);
                }
            });
        }
    }

//     USED FROM:  "startActivityForResult"
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: resultCode = " + resultCode + " - data = " + data);
        // Check which request we're responding to - Check the request was successful - Check the Intent result
        if (requestCode == 0 && resultCode == RESULT_OK && data != null){
            ensureState(data);
        } else {
            loadSharedPreferences();
        }
        //showFullBodyParts();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(HEAD_INDEX_LABEL, headIndex);
        outState.putInt(BODY_INDEX_LABEL, bodyIndex);
        outState.putInt(LEG_INDEX_LABEL, legIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveSharedPreferences();
    }

//    public void ensureState(Bundle bundle) {
//        if(bundle != null && bundle.containsKey(HEAD_INDEX_LABEL)
//                && bundle.containsKey(BODY_INDEX_LABEL)
//                && bundle.containsKey(LEG_INDEX_LABEL)){
//            Log.d(TAG, "loading state from savedInstanceState: " + bundle);
//            headIndex = bundle.getInt(HEAD_INDEX_LABEL, 0);
//            bodyIndex = bundle.getInt(BODY_INDEX_LABEL, 0);
//            legIndex = bundle.getInt(LEG_INDEX_LABEL, 0);
//        } else {
//            loadSharedPreferences();
//        }
//    }

    public void ensureState(Intent intent) {
        if(intent.hasExtra(MainActivity.HEAD_INDEX_LABEL)
                && intent.hasExtra(MainActivity.BODY_INDEX_LABEL)
                && intent.hasExtra(MainActivity.LEG_INDEX_LABEL)){
            Log.d(TAG, "loading state from Intent extra data");
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
        Log.d(TAG, "saveSharedPreferences: headIndex = " + headIndex + ", bodyIndex = " + bodyIndex + ", legIndex = " + legIndex);
        SharedPreferences prefs = getSharedPreferences(ANDROIDME_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HEAD_INDEX_LABEL, headIndex);
        editor.putInt(BODY_INDEX_LABEL, bodyIndex);
        editor.putInt(LEG_INDEX_LABEL, legIndex);
        editor.commit();
    }

    // Define the behavior for onImageSelected
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        // Based on where a user has clicked, store the selected list index for the head, body, and leg BodyPartFragments

        // bodyPartNumber will be = 0 for the head fragment, 1 for the body, and 2 for the leg fragment
        // Dividing by 12 gives us these integer values because each list of images resources has a size of 12
        int bodyPartNumber = position / 12;

        // Store the correct list index no matter where in the image list has been clicked
        // This ensures that the index will always be a value between 0-11
        int listIndex = position - 12 * bodyPartNumber;

        // DONE (5) Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        // The two-pane case will not need a Bundle or Intent since a new activity will not be started;
        // This is all happening in this MainActivity and one fragment will be replaced at a time
        switch (bodyPartNumber) {
            case HEAD_PART_ID:
                headIndex = listIndex;
                if (twoPaneUI) showHeadPart();
                break;
            case BODY_PART_ID:
                bodyIndex = listIndex;
                if (twoPaneUI) showBodyPart();
                break;
            case LEG_PART_ID:
                legIndex = listIndex;
                if (twoPaneUI) showLegPart();
                break;
        }
    }

    private void showFullBodyParts() {
        showHeadPart();
        showBodyPart();
        showLegPart();
    }

    private void showHeadPart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        BodyPartFragment headFragment = new BodyPartFragment();
        headFragment.setChangeListener(this, HEAD_PART_ID);
        headFragment.setImageIds(AndroidImageAssets.getHeads());
        headFragment.setListIndex(headIndex);
        fragmentManager.beginTransaction()
                .replace(R.id.head_container, headFragment)
                .commit();
    }

    private void showBodyPart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        BodyPartFragment bodyFragment = new BodyPartFragment();
        bodyFragment.setChangeListener(this, BODY_PART_ID);
        bodyFragment.setImageIds(AndroidImageAssets.getBodies());
        bodyFragment.setListIndex(bodyIndex);
        fragmentManager.beginTransaction()
                .replace(R.id.body_container, bodyFragment)
                .commit();
    }

    private void showLegPart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        BodyPartFragment legFragment = new BodyPartFragment();
        legFragment.setChangeListener(this, LEG_PART_ID);
        legFragment.setImageIds(AndroidImageAssets.getLegs());
        legFragment.setListIndex(legIndex);
        fragmentManager.beginTransaction()
                .replace(R.id.leg_container, legFragment)
                .commit();
    }

    @Override
    public void newIndex(int index, int partCode) {
        Log.d(TAG, "part index changed: index=" + index + " - partCode = " + partCode);
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
