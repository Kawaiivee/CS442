package org.sheddaquarium.charcounts;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private EditText editText;
    private TextView charCountText;
    private ProgressBar progressBar;
    public static int MAX_CHARS = 50;

    private ArrayList<Auto> autoList = new ArrayList<>();
    private RecyclerView recyclerView; // Layout's recyclerview
    private AutoAdapter mAdapter; // Data to recyclerview adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_CHARS)});
        charCountText = findViewById(R.id.textCount);
        addTextListener();

        makeFakeData();

        recyclerView = findViewById(R.id.recycler);
        mAdapter = new AutoAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    private void addTextListener() {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // Nothing to do here
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int len = s.toString().length();
                String countText = "(" + len + " of " + MAX_CHARS + ")";
                charCountText.setText(countText);
            }
        });
    }

    private void makeFakeData() {
        autoList.add(new Auto("Dodge", "Charger", 2017));
        autoList.add(new Auto("Volkswagen", "Jetta", 2018));
        autoList.add(new Auto("Toyota", "Camry", 2019));
        autoList.add(new Auto("Ford", "Mustang", 2018));
    }

    public void changeProgress(View v) {

        if (((ToggleButton) findViewById(R.id.toggleButton)).isChecked()) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

    }

    //////////////////////////////////////////


    class AutoViewHolder extends RecyclerView.ViewHolder {

        TextView make;
        TextView model;
        TextView year;

        AutoViewHolder(View view) {
            super(view);
            make = view.findViewById(R.id.makeText);
            model = view.findViewById(R.id.modelText);
            year = view.findViewById(R.id.yearText);
        }

    }

    //////////////////////////////////////////

    public class AutoAdapter extends RecyclerView.Adapter<AutoViewHolder> {

        @NonNull
        @Override
        public AutoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.auto_list_entry, parent, false);

            itemView.setOnClickListener(MainActivity.this);

            return new AutoViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AutoViewHolder holder, int position) {
            Auto thisAuto = autoList.get(position);

            holder.make.setText(thisAuto.getMake());
            holder.model.setText(thisAuto.getModel());
            holder.year.setText(String.format("%d", thisAuto.getYear()));

            if (thisAuto.getMake().toLowerCase().trim().equals("volkswagen")) {
                holder.make.setTextColor(getResources().getColor(R.color.colorAccent));
                holder.model.setTextColor(getResources().getColor(R.color.colorAccent));
                holder.year.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                holder.make.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                holder.model.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                holder.year.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }


        }

        @Override
        public int getItemCount() {
            return autoList.size();
        }

    }
}
