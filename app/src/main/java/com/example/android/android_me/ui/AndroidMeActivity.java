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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.android_me.R;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class AndroidMeActivity extends AppCompatActivity {
    private static final String TAG = AndroidMeActivity.class.getSimpleName();

    // DONE (1) Create a layout file that displays one body part image named fragment_head_layout.xmlml
        // This layout should contain a single ImageView

    // DONE (2) Create a new class called HeadFragment to display an image of an Android-Me body part
        // In this class, you'll need to implement an empty constructor and the onCreateView method
        // DONE (3) Show the first image in the list of head images
            // Soon, you'll update this image display code to show any image you want



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        // DONE (5) Create a new HeadFragment instance and display it using the FragmentManager
        HeadFragment headFragment = new HeadFragment();
        BodyFragment bodyFragment = new BodyFragment();
        LegsFragment legsFragment = new LegsFragment();

        // metodo alternativo per utilizzare una sola classe: BodyPartFragment
        // anzichè tre: HeadFragment, BodyFragment, LegsFragment
        // ed un solo layout: fragment_body_layout.xml
//        Bundle headBundle = new Bundle();
//        headBundle.putInt("bodyPartIndex", BodyPartFragment.HEAD_PART_INDEX);
//        BodyPartFragment headFragment = new BodyPartFragment();
//        headFragment.setArguments(headBundle);
//
//        Bundle bodyBundle = new Bundle();
//        bodyBundle.putInt("bodyPartIndex", BodyPartFragment.BODY_PART_INDEX);
//        BodyPartFragment bodyFragment = new BodyPartFragment();
//        bodyFragment.setArguments(bodyBundle);
//
//        Bundle legsBundle = new Bundle();
//        legsBundle.putInt("bodyPartIndex", BodyPartFragment.LEGS_PART_INDEX);
//        BodyPartFragment legsFragment = new BodyPartFragment();
//        legsFragment.setArguments(legsBundle);

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.head_container, headFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.body_container, bodyFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.legs_container, legsFragment).commit();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, getClass().getSimpleName() + "onStop!!!");
    }
}
