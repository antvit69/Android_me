package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.Random;

/**
 * Created by antlap on 07/07/2017.
 */

public class LegsFragment extends Fragment {
    private ImageView mLegsImageView;

    public LegsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_legs_layout, container, false);
        mLegsImageView = (ImageView) rootView.findViewById(R.id.iv_legs);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        int rnd = new Random().nextInt(11);
        mLegsImageView.setImageResource(AndroidImageAssets.getLegs().get(rnd));
    }
}
