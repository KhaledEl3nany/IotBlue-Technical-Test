package com.assem.tests.iotblue.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assem.tests.iotblue.R;

import butterknife.ButterKnife;

public class BookmarksFragment extends Fragment {

    public BookmarksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

}
