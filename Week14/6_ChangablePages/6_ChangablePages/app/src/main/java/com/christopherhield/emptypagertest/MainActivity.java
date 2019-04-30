package com.christopherhield.emptypagertest;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Note: No DrawerView in this example

    private MyPageAdapter pageAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view);

        pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);

        // Add a background image to the view pager
        pager.setBackground(getResources().getDrawable(R.drawable.background, this.getTheme()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Menu selection here creates a new set of "n" fragments
        // where "n" is the menu selection "item" passed in.

        int num;
        if (item.toString().startsWith("Random")) {
            num = 1 + (int) (Math.random() * 100.0);
        } else {
            num = Integer.parseInt(item.toString());
        }

        // Tell page adapter that all pages have changed
        for (int i = 0; i < pageAdapter.getCount(); i++)
            pageAdapter.notifyChangeInPosition(i);

        // Clear the fragments list
        fragments.clear();

        // Create "n" new fragments where "n" is the menu selection "item" passed in.
        for (int i = 0; i < num; i++) {
            fragments.add(MyFragment.newInstance("Fragment " + (i + 1) + " of " + num));
        }

        // Tell the page adapter that the list of fragments has changed
        pageAdapter.notifyDataSetChanged();

        // Set the first fragment to display
        pager.setCurrentItem(0);

        // Remove the view pger background image
        pager.setBackground(null);
        return true;

    }


    // Standard adapter code here
    private class MyPageAdapter extends FragmentPagerAdapter {
        private long baseId = 0;

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public long getItemId(int position) {
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        public void notifyChangeInPosition(int n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }


    }
}
