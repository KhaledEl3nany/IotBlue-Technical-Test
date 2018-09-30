package com.assem.tests.iotblue.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assem.tests.iotblue.Activities.DetailsActivity;
import com.assem.tests.iotblue.App.AppConfig;
import com.assem.tests.iotblue.Models.PlaceBookmarkModel;
import com.assem.tests.iotblue.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesBookmarkAdapter extends RecyclerView.Adapter<PlacesBookmarkAdapter.PlaceHolder> {

    private Context context;
    private ArrayList<PlaceBookmarkModel> placeBookmarkModelArrayList;

    public PlacesBookmarkAdapter(Context context, ArrayList<PlaceBookmarkModel> placeBookmarkModelArrayList) {
        this.context = context;
        this.placeBookmarkModelArrayList = placeBookmarkModelArrayList;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_place_bookmark, parent, false);
        return new PlaceHolder(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
        final PlaceBookmarkModel placeBookmarkModel = placeBookmarkModelArrayList.get(position);
        holder.placeTitle.setText(placeBookmarkModel.getTitle());
        holder.placeLatLan.setText("Lat : " + placeBookmarkModel.getLat() + "\nLon : " + placeBookmarkModel.getLon());

        holder.placeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetailsActivity(placeBookmarkModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeBookmarkModelArrayList.size();
    }

    class PlaceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_place_bookmark_layout)
        LinearLayout placeLayout;
        @BindView(R.id.item_place_bookmark_title)
        TextView placeTitle;
        @BindView(R.id.item_place_bookmark_lat_lan)
        TextView placeLatLan;

        PlaceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void goToDetailsActivity(PlaceBookmarkModel placeBookmarkModel) {
        Intent intent = new Intent(context, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConfig.PLACE_INTENT_KEY, placeBookmarkModel);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
