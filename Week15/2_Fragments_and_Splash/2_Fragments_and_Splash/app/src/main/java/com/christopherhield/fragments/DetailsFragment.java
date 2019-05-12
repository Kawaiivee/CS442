package com.christopherhield.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    // Create a DetailsFragment that contains the data for the selected index
    public static DetailsFragment newInstance(int index) {

        DetailsFragment f = new DetailsFragment();

        // Bundles are used to pass data using a key "index" and an index value
        // to the new Fragment
        Bundle args = new Bundle();
        args.putInt("index", index);

        // Assign key value to the fragment
        f.setArguments(args);

        return f; // Return the new fragment
    }

    public int getShownIndex() {
        // Returns the index from the arguments bundle

        // 0 is the default value in case it isn't there
        return getArguments().getInt("index", 0);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // This is like onCreate is for Activities

        // Inflate/build a new view from the fragment_details layout
        View inflatedFragment = inflater.inflate(R.layout.fragment_details, container, false);

        // Get a reference to the fragment's imageview
        ImageView imageView = inflatedFragment.findViewById(R.id.imageView);

        int shownIndexValue = getShownIndex();
        String imageName = ZooAnimal.get(shownIndexValue).getName().
                                    toLowerCase().
                                    replaceAll(" ", "_").
                                    replaceAll("-", "_");

        int id = imageView.getContext().getResources().
                getIdentifier(imageName, "drawable", imageView.getContext().getPackageName());
        imageView.setImageResource(id);

        // Set the individual data items in the fragment
        TextView title = inflatedFragment.findViewById(R.id.title);
        title.setText(ZooAnimal.get(getShownIndex()).getName());

        TextView latin = inflatedFragment.findViewById(R.id.latin);
        latin.setText(ZooAnimal.get(getShownIndex()).getLatin());

        TextView height = inflatedFragment.findViewById(R.id.height);
        height.setText(ZooAnimal.get(getShownIndex()).getHeight());

        TextView weight = inflatedFragment.findViewById(R.id.weight);
        weight.setText(ZooAnimal.get(getShownIndex()).getWeight());

        TextView distribution = inflatedFragment.findViewById(R.id.distribution);
        distribution.setText(ZooAnimal.get(getShownIndex()).getDistribution());

        TextView habitat = inflatedFragment.findViewById(R.id.habitat);
        habitat.setText(ZooAnimal.get(getShownIndex()).getHabitat());

        TextView wild_diet = inflatedFragment.findViewById(R.id.wild_diet);
        wild_diet.setText(ZooAnimal.get(getShownIndex()).getWild_diet());

        TextView zoo_diet = inflatedFragment.findViewById(R.id.zoo_diet);
        zoo_diet.setText(ZooAnimal.get(getShownIndex()).getZoo_diet());

        TextView text = inflatedFragment.findViewById(R.id.textView);
        text.setText(ZooAnimal.get(getShownIndex()).getDetails());

        return inflatedFragment;
    }
}