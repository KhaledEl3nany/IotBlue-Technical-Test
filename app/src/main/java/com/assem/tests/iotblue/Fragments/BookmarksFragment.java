package com.assem.tests.iotblue.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.assem.tests.iotblue.Adapters.PlacesBookmarkAdapter;
import com.assem.tests.iotblue.App.AppConfig;
import com.assem.tests.iotblue.Models.PlaceBookmarkModel;
import com.assem.tests.iotblue.R;
import com.assem.tests.iotblue.Utils.ViewsUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarksFragment extends Fragment {

    private static final String TAG = BookmarksFragment.class.getSimpleName();
    // Vars
    ArrayList<PlaceBookmarkModel> placeBookmarkModelArrayList;
    PlacesBookmarkAdapter placesBookmarkAdapter;
    // Firebase
    private DatabaseReference mRef;
    // Views
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.fragment_bookmarks_recycler_view)
    RecyclerView bookmarksRecyclerView;
    @BindView(R.id.fragment_bookmarks_empty_recycler)
    ImageView emptyRecyclerPlaceHolder;


    public BookmarksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getBookmarks() {
        mRef.child(AppConfig.BOOKMARKS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                placeBookmarkModelArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlaceBookmarkModel placeBookmarkModel = snapshot.getValue(PlaceBookmarkModel.class);
                    placeBookmarkModelArrayList.add(placeBookmarkModel);
                    placesBookmarkAdapter.notifyDataSetChanged();
                }
                Collections.reverse(placeBookmarkModelArrayList);
                toggleLayout(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
                Log.d(TAG, databaseError.getDetails());
                Toast.makeText(getContext(), R.string.error_getting_data, Toast.LENGTH_LONG).show();
            }
        });
        bookmarksRecyclerView.setAdapter(placesBookmarkAdapter);
    }

    private void init() {
        toggleLayout(false);
        mRef = FirebaseDatabase.getInstance().getReference();
        placeBookmarkModelArrayList = new ArrayList<>();
        placesBookmarkAdapter = new PlacesBookmarkAdapter(getContext(), placeBookmarkModelArrayList);
        new ViewsUtils().setupLinearVerticalRecView(getContext(), bookmarksRecyclerView);
        getBookmarks();
    }

    private void toggleLayout(boolean flag) {
        if (flag) {
            bookmarksRecyclerView.setVisibility(View.VISIBLE);
            progressLayout.setVisibility(View.GONE);
            progressBar.hide();
            if (placeBookmarkModelArrayList.isEmpty()) {
                emptyRecyclerPlaceHolder.setVisibility(View.VISIBLE);
                bookmarksRecyclerView.setVisibility(View.GONE);
            } else {
                emptyRecyclerPlaceHolder.setVisibility(View.GONE);
                bookmarksRecyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            progressLayout.setVisibility(View.VISIBLE);
            progressBar.show();
            bookmarksRecyclerView.setVisibility(View.GONE);
        }
    }
}
