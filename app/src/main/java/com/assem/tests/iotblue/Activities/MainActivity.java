package com.assem.tests.iotblue.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.assem.tests.iotblue.Adapters.ViewPagerAdapter;
import com.assem.tests.iotblue.Fragments.BookmarksFragment;
import com.assem.tests.iotblue.Fragments.MapFragment;
import com.assem.tests.iotblue.R;
import com.assem.tests.iotblue.Utils.ViewsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // Views
    @BindView(R.id.activity_main_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.activity_main_view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MapFragment(), getString(R.string.map));
        viewPagerAdapter.addFragment(new BookmarksFragment(), getString(R.string.bookmarks));
        new ViewsUtils().setupTabLayout(viewPager, viewPagerAdapter, tabLayout, 0);
    }
}
