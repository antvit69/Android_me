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

package com.example.android.android_me.data;

import com.example.android.android_me.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Class for storing all the image drawable resources in ArrayLists
public class AndroidImageAssets {

    // Lists for all AndroidMe images
    // Broken down into heads, bodies, legs, and all images

    private static final List<Integer> heads = getIntegerList(R.drawable.head1,
            R.drawable.head2,
            R.drawable.head3,
            R.drawable.head4,
            R.drawable.head5,
            R.drawable.head6,
            R.drawable.head7,
            R.drawable.head8,
            R.drawable.head9,
            R.drawable.head10,
            R.drawable.head11,
            R.drawable.head12);

    private static final List<Integer> bodies = getIntegerList(R.drawable.body1,
            R.drawable.body2,
            R.drawable.body3,
            R.drawable.body4,
            R.drawable.body5,
            R.drawable.body6,
            R.drawable.body7,
            R.drawable.body8,
            R.drawable.body9,
            R.drawable.body10,
            R.drawable.body11,
            R.drawable.body12);

    private static final List<Integer> legs = getIntegerList(R.drawable.legs1,
            R.drawable.legs2,
            R.drawable.legs3,
            R.drawable.legs4,
            R.drawable.legs5,
            R.drawable.legs6,
            R.drawable.legs7,
            R.drawable.legs8,
            R.drawable.legs9,
            R.drawable.legs10,
            R.drawable.legs11,
            R.drawable.legs12 );

    private static final List<Integer> all = new ArrayList<Integer>() {{
        addAll(heads);
        addAll(bodies);
        addAll(legs);
    }};


    // Getter methods that return lists of all head images, body images, and leg images

    public static List<Integer> getHeads() {
        return heads;
    }

    public static List<Integer> getBodies() {
        return bodies;
    }

    public static List<Integer> getLegs() {
        return legs;
    }

    // Returns a list of all the images combined: heads, bodies, and legs in that order
    public static List<Integer> getAll() {
        return all;
    }

    public static List<Integer> getIntegerList(Integer... args){
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.addAll(Arrays.asList(args));
        return list;
    }

}