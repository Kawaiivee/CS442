package com.example.multinotesapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;

public class NoteViewHolder extends RecyclerView.ViewHolder{
    public TextView lastSaveDate;
    public TextView noteTitle;
    public TextView noteText;

    public NoteViewHolder(View v){
        super(v);
        lastSaveDate = v.findViewById(R.id.lastSaveDate);
        noteTitle = v.findViewById(R.id.noteTitle);
        noteText = v.findViewById(R.id.noteText);
    }
}
