package com.example.stockwatch;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {

    private final List<Stock> stockList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StockAdapter stockAdapter;

    private SwipeRefreshLayout swiper;
    private int stockSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        stockAdapter = new StockAdapter(stockList, this);

        recyclerView.setAdapter(stockAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swiper = findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });
        //Need to load some JSON file stuff here

        //Test Data for recyclerView
        double num = -12.87;
        for(int i = 0; i < 4; i++){
            num = num+i;
            stockList.add(new Stock("Symbol" + i, "Name" + i, num, num, num));
        }
        stockList.add(new Stock("RA", "ROME AIR", 52.3, 22.7, 5.0));
        stockAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop(){
        //Todo
        super.onStop();
    }

    public void doRefresh(){
        Collections.shuffle(stockList);
        stockAdapter.notifyDataSetChanged();
        swiper.setRefreshing(false);
        Toast.makeText(this, "Updating Stocks", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v){
        stockSelected = recyclerView.getChildLayoutPosition(v);

        Stock currentStock = stockList.get(stockSelected);
        Toast.makeText(this, currentStock.getSymbol() + "clicked", Toast.LENGTH_SHORT).show();
        //Do things here when stock is clicked --update stocks

        stockAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onLongClick(View v){
        AlertDialog.Builder ADB = new AlertDialog.Builder(this);
        final int position = recyclerView.getChildLayoutPosition(v);

        //Dialog Box YES = Delete, No = Don't Delete
        ADB.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stockList.remove(position);
                //changes have been made to arraylist
                //notify adapter!
                stockAdapter.notifyDataSetChanged();
            }
        });

        ADB.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Nothing to do here! Go back
            }
        });

        TextView symbol = v.findViewById(R.id.symbol);
        ADB.setTitle("Delete Stock");
        ADB.setMessage("Delete Stock Symbol: " + symbol.getText().toString() + " :?");
        AlertDialog AD = ADB.create();
        AD.show();
        stockAdapter.notifyDataSetChanged();;
        return true;
    }

    //Menu Stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Do stuff here to add a stock (dialog box the shit out of this)
        //This is going to use the hashtable to search for stocks via key value or value key
        //symbol-name name-symbol
        return super.onOptionsItemSelected(item);
    }


}
