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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BodyPartFragment extends Fragment {
    private static final String TAG = BodyPartFragment.class.getSimpleName();
    private static final Random RANDOM = new Random();

    private static final String BODY_PARTS = "body_parts";
    private static final String CURRENT_BODY_PART_INDEX = "current_body_part_index";

    private List<Integer> mImageResources;
    private int mImageIndex = 0;
    private ImageView mImageView;

    // DONE (1) Create a setter method and class variable to set and store of a list of image resources

    // DONE (2) Create another setter method and variable to track and set the index of the list item to display
        // ex. index = 0 is the first image id in the given list , index 1 is the second, and so on

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public BodyPartFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mImageResources = savedInstanceState.getIntegerArrayList(BODY_PARTS);
            mImageIndex = savedInstanceState.getInt(CURRENT_BODY_PART_INDEX);
            Log.d(TAG, "onCreateView: mImageIndex = " + mImageIndex);
        }

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        // Get a reference to the ImageView in the fragment layout
        mImageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        // Set the image to the first in our list of head images
        //mImageView.setImageResource(AndroidImageAssets.getHeads().get(mImageIndex));

        // DONE (3) If a list of image ids exists, set the image resource to the correct item in that list
        // Otherwise, create a Log statement that indicates that the list was not found
        showBodyPart();

        addClickListener();

        // Return the rootView
        return rootView;
    }



    public void setImages(List<Integer> imageResources){
        if(imageResources != null){
            mImageResources = imageResources;
        } else {
            mImageResources.clear();
        }
    }

    public void setImageIndex(int imageIndex){
        if(mImageResources != null && imageIndex > -1 && imageIndex < mImageResources.size()){
            mImageIndex = imageIndex;
        } else {
            mImageIndex = 0;
        }
    }

    public void setRandomImageIndex() {
        setImageIndex(RANDOM.nextInt(AndroidImageAssets.getBodies().size()));
    }

    private void addClickListener() {
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextBodyPart();
            }
        });
    }

    private void showNextBodyPart() {
        ++mImageIndex;
        showBodyPart();
    }

    private void showBodyPart() {
        if(mImageView != null && mImageResources != null && mImageResources.size() > 0){
            mImageIndex = mImageIndex % mImageResources.size();
            mImageView.setImageResource(mImageResources.get(mImageIndex));
        } else {
            Log.e(TAG, "A list of images doesn't exists.");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: mImageIndex = " + mImageIndex);
        outState.putInt(CURRENT_BODY_PART_INDEX, mImageIndex);
        outState.putIntegerArrayList(BODY_PARTS, (ArrayList<Integer>)mImageResources);
    }

}
