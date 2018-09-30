package com.assem.tests.iotblue.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.assem.tests.iotblue.Adapters.ViewPagerAdapter;
import com.assem.tests.iotblue.App.MyApplication;
import com.assem.tests.iotblue.Fragments.BookmarksFragment;
import com.assem.tests.iotblue.Fragments.MapFragment;
import com.assem.tests.iotblue.R;
import com.assem.tests.iotblue.Utils.ConnectivityReceiver;
import com.assem.tests.iotblue.Utils.PermissionUtil;
import com.assem.tests.iotblue.Utils.ViewsUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    // Vars
    private static final int ERROR_DIALOG_REQUEST = 9001;
    // Views
    @BindView(R.id.activity_main_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.activity_main_view_pager)
    ViewPager viewPager;
    @BindView(R.id.no_connection_layout)
    LinearLayout noConnectionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/pt_sans.ttf").setFontAttrId(R.attr.fontPath).build());
        init();
    }

    private void init() {
//        if (checkGooglePlayServices()) {
//        new PermissionUtil().requestPermissions(this);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(new MapFragment(), getString(R.string.map));
            viewPagerAdapter.addFragment(new BookmarksFragment(), getString(R.string.bookmarks));
            new ViewsUtils().setupTabLayout(viewPager, viewPagerAdapter, tabLayout, 0);
//        } else {
//            Toast.makeText(this, R.string.not_supported, Toast.LENGTH_LONG).show();
////            finish();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver(this);
        registerReceiver(connectivityReceiver, intentFilter);
        /*register connection status listener*/
        MyApplication.getInstance(this).setConnectivityListener(this);
    }

    private void isConnected(boolean isConnected) {
        if (isConnected) {
            noConnectionLayout.setVisibility(View.GONE);
        } else {
            noConnectionLayout.setVisibility(View.VISIBLE);
        }
    }


    public boolean checkGooglePlayServices() {
        Log.d(TAG, "isServiceOk: Checking google play services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS) {
            // everything is fine and the user can make map requests
            Log.d(TAG, "isServiceOk:  google play services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServiceOk:  an error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map request, update google play service version", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        isConnected(isConnected);
    }
}
