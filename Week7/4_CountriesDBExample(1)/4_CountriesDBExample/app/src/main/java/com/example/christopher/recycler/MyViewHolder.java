package com.example.christopher.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Christopher on 1/30/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView country;
    public TextView capital;
    public TextView population;
    public TextView region;
    public TextView subRegion;

    public MyViewHolder(View view) {
        super(view);
        country = (TextView) view.findViewById(R.id.country);
        capital = (TextView) view.findViewById(R.id.capital);
        population = (TextView) view.findViewById(R.id.population);
        region = (TextView) view.findViewById(R.id.region);
        subRegion = (TextView) view.findViewById(R.id.subRegion);
    }

}
