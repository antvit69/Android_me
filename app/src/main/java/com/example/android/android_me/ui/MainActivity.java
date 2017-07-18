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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This activity is responsible for displaying the master list of all images
// Implement the MasterListFragment callback, CallbackClickListener
public class MainActivity extends AppCompatActivity implements MasterListFragment.CallbackClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final Bundle bundle = new Bundle();

    public static final String HEAD_INDEX = "head_index";
    public static final String BODY_INDEX = "body_index";
    public static final String LEGS_INDEX = "legs_index";

    public static boolean explicit_start = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate (see launchMode in AndroidManifest.xml)");
        setContentView(R.layout.activity_main);
        initBundle();

//        Button next = (Button) findViewById(R.id.bu_next);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickNext();
//            }
//        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent (see launchMode in AndroidManifest.xml)");
    }

    private void initBundle(){
        if(bundle.size() == 0){
            bundle.putInt(HEAD_INDEX, 0);
            bundle.putInt(BODY_INDEX, 0);
            bundle.putInt(LEGS_INDEX, 0);
        }
    }

    // Define the behavior for onImageSelected
    public void onImageSelected(int position) {

        // DONE (2) Based on where a user has clicked, store the selected list index for the head, body, and leg BodyPartFragments
        int listPart = position / AndroidImageAssets.getHeads().size(); //0 = Head;  1 = Body;  2 = Legs
        int partIndex = position - AndroidImageAssets.getHeads().size() * listPart;

        // DONE (3) Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
        String[] msgListPart = new String[]{"Head", "Body", "Legs"};
        switch(listPart){
            case 0 : //Head
                bundle.putInt(HEAD_INDEX, partIndex);
                break;
            case 1 : //Body
                bundle.putInt(BODY_INDEX, partIndex);
                break;
            case 2 : //Legs
                bundle.putInt(LEGS_INDEX, partIndex);
                break;
            default:
                listPart = 0;
        }

        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Using " + msgListPart[listPart] + partIndex, Toast.LENGTH_SHORT).show();
    }


    // DONE (4) Get a reference to the "Next" button and launch the intent when this button is clicked
    @Override
    public void onClickNext() {
        if(explicit_start){ //--> START EXPLICIT INTENT
            launchExplictIntent();
        } else {            //--> START IMPLICIT INTENT
            launchImplictIntent();
        }
        explicit_start = !explicit_start;
    }

    //--> START EXPLICIT INTENT
    private void launchExplictIntent() {
        Log.d(TAG, "starting Explict Intent");
        Intent intent = new Intent(MainActivity.this, AndroidMeActivity.class);
        intent.putExtra(Intent.ACTION_ATTACH_DATA, bundle);
        startActivity(intent);
    }

    //--> START IMPLICIT INTENT
    private void launchImplictIntent() {
        Log.d(TAG, "starting Implict Intent");

        // using predefined ACTION
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_ALTERNATIVE);

        // using custom ACTION
        String CUSTOM_ACTION = "com.android_me.NAVIGATE_ACTION";
        Intent intent = new Intent(CUSTOM_ACTION);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(Intent.ACTION_ATTACH_DATA, bundle);
        // Verify that the intent will resolve to an activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
