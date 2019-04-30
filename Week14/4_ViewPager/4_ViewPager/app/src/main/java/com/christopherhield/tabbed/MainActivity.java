package com.christopherhield.tabbed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the five
        // primary sections of the activity.
        // The SectionsPagerAdapter class is defined down below.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container); // Get ViewPager
        mViewPager.setAdapter(mSectionsPagerAdapter);  // Set the adapter

    }
//////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * A placeholder fragment containing a simple view.
     * It doesn't do much, but this is just an example
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        /**
         * Returns a new instance of this fragment for the given section
         * number. The 'sectionNumber' parameter indicates what page to
         * display: 1, 2 , 3, etc.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {

            PlaceholderFragment fragment = new PlaceholderFragment();

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }


        // The onCreateView is like Activity's onCreate for a Fragment
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Use the inflater passed in to build (inflate) the fragment
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            // Get  reference to the textview in the page layout
            TextView textView = rootView.findViewById(R.id.section_label);

            // The fragment's arguments (getArguments()) contains a field with the key
            // ARG_SECTION_NUMBER that holds the value to put in the textview
            if (getArguments() != null){
                textView.setText(getString(R.string.section_format,
                        getArguments().getInt(ARG_SECTION_NUMBER)));
            }
            return rootView;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Returns a PlaceholderFragment (defined as a static inner class above).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Returns the total number of pages. This is hard-coded here but
            // often it is the size of a collection.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "SECTION " + position;
        }
    }
}
