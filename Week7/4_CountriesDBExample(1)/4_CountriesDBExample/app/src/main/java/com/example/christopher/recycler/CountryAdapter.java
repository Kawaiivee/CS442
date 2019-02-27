package com.example.christopher.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "CountryAdapter";
    private List<Country> countryList;
    private MainActivity mainAct;

    public CountryAdapter(List<Country> empList, MainActivity ma) {
        this.countryList = empList;
        mainAct = ma;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_entry, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Country country = countryList.get(position);
        holder.country.setText(country.getName());
        holder.capital.setText(String.format("Capital: %s", country.getCapital()));
        holder.population.setText(String.format("Population: %,d", country.getPopulation()));
        holder.region.setText(country.getRegion());
        holder.subRegion.setText(country.getSubRegion());
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

}