package com.example.multinotesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private List<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        noteAdapter = new NoteAdapter(noteList, this);

        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteList.add(new Note("1/1/2019", "Example Note", "Foo Baz Bar Fizz Buzz"));
    }

    //Onclick Listeners
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks
        int pos = recyclerView.getChildLayoutPosition(v);
        Note N = noteList.get(pos);

        Toast.makeText(v.getContext(), "SHORT " + N.getNoteTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        int pos = recyclerView.getChildLayoutPosition(v);
        Note N = noteList.get(pos);

        Toast.makeText(v.getContext(), "LONG " + N.getNoteTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }

    //Other Activities
    public void openAboutActivity(View v) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
