package com.example.christopher.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView author;
    public TextView isbn;
    public TextView pub_year;
    public TextView cost;

    MyViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.title);
        author = view.findViewById(R.id.author);
        isbn = view.findViewById(R.id.isbn);
        pub_year = view.findViewById(R.id.publisher_year);
        cost = view.findViewById(R.id.cost);
    }

}
