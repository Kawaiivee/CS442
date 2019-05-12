package com.example.inspirationrewards;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileViewHolder> {
    private List<Profile> profileList;
    private LeaderboardActivity leaderboardActivity;

    public ProfileAdapter(List<Profile> pl, LeaderboardActivity la){
        this.profileList = pl;
        leaderboardActivity = la;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.leaderboard_list_row, parent, false);
        itemView.setOnClickListener(leaderboardActivity);
        itemView.setOnLongClickListener(leaderboardActivity);
        return new ProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position){
        Profile profile = profileList.get(position);

        //points from int to a string
        String strPoints = intToString(profile.getPoints());

        //STILL NEED TO DO IMAGEVIEW!!! thumbnail
        holder.name.setText(profile.getLastname() + ", " + profile.getFirstname());
        holder.position.setText(profile.getPosition());
        holder.department.setText(profile.getDepartment());
        holder.points.setText(strPoints);
        holder.thumbnail.setImageBitmap(BitmapFactory.decodeByteArray(profile.getPhoto(), 0, profile.getPhoto().length));
    }

    public static String intToString(int i){
        String s = "" + i;
        return s;
    }

    @Override
    public int getItemCount(){
        return profileList.size();
    }
}
