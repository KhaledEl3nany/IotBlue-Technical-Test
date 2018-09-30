package com.assem.tests.iotblue.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assem.tests.iotblue.App.AppConfig;
import com.assem.tests.iotblue.Models.BookmarkModel;
import com.assem.tests.iotblue.Models.PlaceBookmarkModel;
import com.assem.tests.iotblue.Networking.ApiClient;
import com.assem.tests.iotblue.Networking.ApiInterface;
import com.assem.tests.iotblue.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    // Views
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    @BindView(R.id.details_activity_city_name)
    TextView cityNameTxt;
    @BindView(R.id.details_activity_city_weather)
    TextView weatherTxt;
    @BindView(R.id.details_activity_temperature)
    TextView temperatureTxt;
    @BindView(R.id.details_activity_humidity)
    TextView humidityTxt;
    @BindView(R.id.details_activity_wind)
    TextView windTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        toggleLayout(false);
        // toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
//        toolbarTitle.setText(levelObj.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void getPlaceWeather(String lat, String lan) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BookmarkModel> bookmarkModelCall = apiService.getBookmarkData(lat, lan, AppConfig.API_KEY, AppConfig.METRIC);
        bookmarkModelCall.enqueue(new Callback<BookmarkModel>() {
            @Override
            public void onResponse(@NonNull Call<BookmarkModel> call, @NonNull Response<BookmarkModel> response) {
                BookmarkModel bookmarkModel = response.body();
                if (bookmarkModel != null) {
                    cityNameTxt.setText(bookmarkModel.getName());
                    weatherTxt.setText(bookmarkModel.getWeather().get(0).getDescription());
                    temperatureTxt.setText(String.valueOf(bookmarkModel.getMain().getTemp()));
                    humidityTxt.setText(String.valueOf(bookmarkModel.getMain().getHumidity()));
                    windTxt.setText(String.valueOf(bookmarkModel.getWind().getSpeed()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookmarkModel> call, @NonNull Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
        toggleLayout(true);
    }

    private void toggleLayout(boolean flag) {
        if (flag) {
            progressLayout.setVisibility(View.GONE);
            progressBar.hide();
        } else {
            progressLayout.setVisibility(View.VISIBLE);
            progressBar.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
        Intent bundleIntent = getIntent();
        PlaceBookmarkModel placeBookmarkModel = (PlaceBookmarkModel) bundleIntent.getSerializableExtra(AppConfig.PLACE_INTENT_KEY);
        if (placeBookmarkModel != null) {
            getPlaceWeather(String.valueOf(placeBookmarkModel.getLat()), String.valueOf(placeBookmarkModel.getLon()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
