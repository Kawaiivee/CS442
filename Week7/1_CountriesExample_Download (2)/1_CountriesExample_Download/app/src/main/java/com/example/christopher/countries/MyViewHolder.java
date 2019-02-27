package com.example.christopher.countries;

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
        country = view.findViewById(R.id.country);
        capital = view.findViewById(R.id.capital);
        population = view.findViewById(R.id.population);
        region = view.findViewById(R.id.region);
        subRegion = view.findViewById(R.id.subRegion);
    }

}
