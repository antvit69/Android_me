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

import android.content.Intent;
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

import static com.example.android.android_me.ui.AppState.BODY_PART_ID;
import static com.example.android.android_me.ui.AppState.HEAD_PART_ID;
import static com.example.android.android_me.ui.AppState.LEG_PART_ID;
import static com.example.android.android_me.ui.AppState.MYTAG;

// This activity is responsible for displaying the master list of all images
// Implement the MasterListFragment callback, OnImageClickListener
public class MainActivity extends AppCompatActivity implements View.OnClickListener, MasterListFragment.OnImageClickListener {
    private static final String TAG = MYTAG + MainActivity.class.getSimpleName();

    private AppState appState;

    private boolean twoPaneUI;


    // DONE (3) Create a variable to track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appState = AppState.getInstance();
        appState.setContext(this);
        Log.d(TAG, "onCreate: ");

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
            nextButton.setOnClickListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        appState.persistState();
    }

    private void showFullBodyParts() {
        showHeadPart();
        showBodyPart();
        showLegPart();
    }

    private void showHeadPart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        BodyPartFragment headFragment = new BodyPartFragment();
        headFragment.setChangeListener(appState, HEAD_PART_ID);
        headFragment.setImageIds(AndroidImageAssets.getHeads());
        headFragment.setListIndex(appState.getHeadIndex());
        fragmentManager.beginTransaction()
                .replace(R.id.head_container, headFragment)
                .commit();
    }

    private void showBodyPart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        BodyPartFragment bodyFragment = new BodyPartFragment();
        bodyFragment.setChangeListener(appState, BODY_PART_ID);
        bodyFragment.setImageIds(AndroidImageAssets.getBodies());
        bodyFragment.setListIndex(appState.getBodyIndex());
        fragmentManager.beginTransaction()
                .replace(R.id.body_container, bodyFragment)
                .commit();
    }

    private void showLegPart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        BodyPartFragment legFragment = new BodyPartFragment();
        legFragment.setChangeListener(appState, LEG_PART_ID);
        legFragment.setImageIds(AndroidImageAssets.getLegs());
        legFragment.setListIndex(appState.getLegIndex());
        fragmentManager.beginTransaction()
                .replace(R.id.leg_container, legFragment)
                .commit();
    }

    // Define the behavior for onImageSelected of interface MasterListFragment.OnImageClickListener
    @Override
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
                appState.setHeadIndex(listIndex);
                if (twoPaneUI) showHeadPart();
                break;
            case BODY_PART_ID:
                appState.setBodyIndex(listIndex);
                if (twoPaneUI) showBodyPart();
                break;
            case LEG_PART_ID:
                appState.setLegIndex(listIndex);
                if (twoPaneUI) showLegPart();
                break;
        }
    }

    // Define the behavior for onClick of interface View.OnClickListener
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.next_button :
                Intent intent = new Intent(MainActivity.this, AndroidMeActivity.class);
                startActivity(intent);
                break;
        }
    }

}