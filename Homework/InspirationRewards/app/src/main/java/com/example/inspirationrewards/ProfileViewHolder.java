package com.example.inspirationrewards;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileViewHolder extends RecyclerView.ViewHolder{
    public ImageView thumbnail;
    public TextView name;
    public TextView points;
    public TextView position;
    public TextView department;

    public ProfileViewHolder(View v){
        super(v);
        thumbnail = v.findViewById(R.id.llrThumbnail);
        name = v.findViewById(R.id.llrLastname);
        points = v.findViewById(R.id.llrPoints);
        position = v.findViewById(R.id.llrPosition);
        department = v.findViewById(R.id.llrDepartment);
    }
}
