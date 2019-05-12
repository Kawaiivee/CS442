package com.example.newsgateway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Source> sourceList = new ArrayList<>();
    private ArrayList<String> sourceNameList = new ArrayList<>();

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private List<Fragment> fragmentList = new ArrayList<>();
    private MainPageAdapter pageAdapter;
    private ViewPager viewPager;
    private ArticleAsyncTask articleAsyncTask;

    //receiever response and request -- use for intent filters onCreate --
    private NewsReceiver newsReceiver;

    //ArrayAdapter
    private ArrayAdapter<String> sourceArrayAdapter;

    //Menu
    Menu m;

    //codes for intent filters
    static final String REQUEST = "REQUEST";
    static final String RESPONSE = "RESPONSE";

    //codes
    static final String STORY = "STORY";
    static final String SOURCE = "SOURCE";

    HashMap<String, ArrayList<Source>> sourceHash = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create service with correspoding class
        Intent intent = new Intent(MainActivity.this, NewsService.class);
        startService(intent);

        //NewsReceiever instantiation
        newsReceiver = new NewsReceiver();
        registerReceiver(newsReceiver, new IntentFilter(RESPONSE));


        //ActionBar v7
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.list);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        //Drawer Layout shit
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.sourceDrawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawerOpen, R.string.drawerClose);

        //Async Task To Get Sources
        SourceAsyncTask sourceAsyncTask = new SourceAsyncTask(this);
        sourceAsyncTask.execute("");

        sourceArrayAdapter = new ArrayAdapter<>(this, R.layout.drawer_item, sourceNameList);
        drawerList.setAdapter(sourceArrayAdapter);
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                selectSource(position);
            }
        });

        fragmentList = new ArrayList<>();
        pageAdapter = new MainPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPagerView);
        viewPager.setAdapter(pageAdapter);

        if(savedInstanceState != null){
            fragmentList = (List<Fragment>) savedInstanceState.getSerializable("fragmentList");
            setTitle(savedInstanceState.getString("title"));
            pageAdapter.notifyDataSetChanged();
            for(int i=0; i<pageAdapter.getCount(); i++){
                pageAdapter.notifyChangeInPosition(i);
            }
        }
    }

    //Menu Used for News Categories (hardcoded categories for now)
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.add("all");
        m = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        sourceNameList.clear();

        ArrayList<Source> temp = sourceHash.get(item.getTitle());

        for (int i = 0; i < temp.size(); i++) {
            sourceNameList.add(temp.get(i).getName());
        }

        sourceArrayAdapter.notifyDataSetChanged();


        return true;
    }

    public class NewsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){

            Toast.makeText(MainActivity.this, "Receiver", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "intent: " + intent.getAction().equals(RESPONSE));

            switch(intent.getAction()){
                case(RESPONSE):
                    ArrayList<Article> articleList = (ArrayList<Article>) intent.getSerializableExtra("articleList");
                    Toast.makeText(context, "onReceive called", Toast.LENGTH_SHORT).show();
                    for(int i=0;i<pageAdapter.getCount();i++){
                        pageAdapter.notifyChangeInPosition(i);
                    }

                    fragmentList.clear();

                    for(int i=0; i<articleList.size(); i++){
                        fragmentList.add(ArticleFragment.newInstance(articleList.get(i)));
                    }
                    pageAdapter.notifyDataSetChanged();
                    viewPager.setCurrentItem(0);
            }
        }
    }

    private class MainPageAdapter extends FragmentPagerAdapter{
        private long id = 0;
        public MainPageAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return fragmentList.indexOf(object);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public long getItemId(int position) {
            return id+position;
        }

        //this method is used for keeping track of what fragment we are on/shifted to
        public void notifyChangeInPosition(int n) {
            id += getCount() + n;
        }
    }


    //MainAct Helpers
    private void selectSource(int position){
        String sourceTitle = sourceList.get(position).getName();
        Toast.makeText(this, "Source " + sourceTitle, Toast.LENGTH_SHORT).show();
        setTitle(sourceTitle);

        Intent request= new Intent();
        request.setAction(MainActivity.REQUEST);
        request.putExtra("source", sourceList.get(position).getId());
        sendBroadcast(request);

        drawerLayout.closeDrawer(drawerList);
    }

    public void addSources(ArrayList<Source> s){
        ArrayList<String> uniqCategories = new ArrayList<>();
        for (int i = 0; i < s.size(); i++ ) {
            if (uniqCategories.indexOf(s.get(i).getCategory()) < 0) {
                uniqCategories.add(s.get(i).getCategory());
            }
            sourceNameList.add(s.get(i).getName());
        }

        for (int i = 0; i < uniqCategories.size(); i++) {
            ArrayList<Source> categoryList = new ArrayList<>();
            for (int j = 0; j < s.size(); j++) {
                if (uniqCategories.get(i).equals(s.get(j).getCategory())) {
                    categoryList.add(s.get(j));
                }
            }

            sourceHash.put(uniqCategories.get(i), categoryList);
        }

        for (String key: sourceHash.keySet()) {
            m.add(key);
        }

        sourceHash.put("all", s);

        sourceList = s;
        sourceArrayAdapter.notifyDataSetChanged();
    }

    public void clearSource(){
        sourceList.clear();
        sourceNameList.clear();
    }

    //Config for landscape
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("fragments", (Serializable) fragmentList);
        outState.putString("title",getTitle().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(newsReceiver);
        Intent intent = new Intent(MainActivity.this, NewsService.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}