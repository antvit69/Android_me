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

import java.util.List;
import java.util.Random;

/**
 * Created by antlap on 07/07/2017.
 */

public class BodyPartFragment extends Fragment {
    private static final String TAG = BodyPartFragment.class.getSimpleName();

    public static final int HEAD_PART_INDEX = 0;
    public static final int BODY_PART_INDEX = 1;
    public static final int LEGS_PART_INDEX = 2;

    private int mBodyPartIndex;
    private ImageView mBodyPartImageView;
    private View mRootView;

    public BodyPartFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = this.getArguments();
        if (arguments != null) {
            mBodyPartIndex = arguments.getInt("bodyPartIndex", HEAD_PART_INDEX);
        }

        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_body_part_layout, container, false);
        mBodyPartImageView = (ImageView) mRootView.findViewById(R.id.iv_body_part);
        return mRootView;
    }

    private int getBodyPartImages(int index){
        List<Integer> bodyPartImages = AndroidImageAssets.getHeads();
        switch (mBodyPartIndex){
            case HEAD_PART_INDEX :
                break;
            case BODY_PART_INDEX :
                bodyPartImages = AndroidImageAssets.getBodies();
                break;
            case LEGS_PART_INDEX :
                bodyPartImages = AndroidImageAssets.getLegs();
                break;
        }
        return bodyPartImages.get(index);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, getClass().getSimpleName() + "onStop!!!");
        mBodyPartImageView.invalidate();
        mBodyPartImageView = null;
        mRootView.invalidate();
        mRootView = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        int rnd = new Random().nextInt(AndroidImageAssets.getBodyPartCount());
        mBodyPartImageView.setImageResource(getBodyPartImages(rnd));
    }
}
