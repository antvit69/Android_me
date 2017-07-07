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
 * Created by antlap on 04/07/2017.
 */

public class HeadFragment extends Fragment {

    private ImageView mHeadImageView;

    public HeadFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_head_layout, container, false);
        mHeadImageView = (ImageView) rootView.findViewById(R.id.iv_head);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        int rnd = new Random().nextInt(11);
        mHeadImageView.setImageResource(AndroidImageAssets.getHeads().get(rnd));
    }
}
