package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

/**
 * Created by antlap on 12/07/2017.
 */

public class MasterListFragment extends Fragment {

    private MasterListAdapter mAdapter;

    public MasterListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        mAdapter = new MasterListAdapter(getContext(), AndroidImageAssets.getAll());
        GridView gridview = (GridView) rootView.findViewById(R.id.gv_master_list);
        gridview.setAdapter(mAdapter);

        return rootView;
    }
}
