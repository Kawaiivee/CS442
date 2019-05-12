package com.christopherhield.fragments;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NameListFragment extends ListFragment {

    // This represents the animal list

    // True or False depending on if we are in horizontal (dual pane) mode
    boolean dualPane;

    // Currently selected item in the ListView
    int currentSelectedIndex = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // An ArrayAdapter connects the array to our ListView
        // getActivity() returns a Context so we have the resources needed
        // We pass a default list item text view to put the data in and the
        // array
        ArrayAdapter<String> arrayToListViewAdapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        ZooAnimal.getAllNames());

        // Connect the ListView to our data
        setListAdapter(arrayToListViewAdapter);

        // Check if the FrameLayout with the id details exists
        View detailsFrame = getActivity().findViewById(R.id.details);

        // Set dualPane based on whether you are in the horizontal layout
        // If the detailsFrame exists and  it is visible - we are in dual-pane mode
        if (detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE)
            dualPane = true;

        // If the screen is rotated onSaveInstanceState() below will store the
        // most recent selection. Get the value attached to the "curChoice" key and
        // store it as currentSelectedIndex
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            currentSelectedIndex = savedInstanceState.getInt("curChoice", 0);
        }

        if (dualPane) {
            // CHOICE_MODE_SINGLE allows one item in the ListView to be selected at a time
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            // Send the item selected to showDetails so the right hero info is shown
            showDetails(currentSelectedIndex);
        }
    }

    // Called every time the screen orientation changes or Android kills an Activity
    // to conserve resources
    // We save the last item selected in the list here and attach it to the key curChoice
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", currentSelectedIndex);
    }

    // When a list item is clicked we want to change the hero info
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    // Shows the data
    void showDetails(int index) {

        // The most recently selected hero in the ListView is sent
        currentSelectedIndex = index;

        // Check if we are in horizontal mode and if yes show the ListView and
        // the animal data
        if (dualPane) { // show both

            // Make the currently selected item highlighted
            getListView().setItemChecked(index, true);

            DetailsFragment details = (DetailsFragment)
                    getFragmentManager().findFragmentById(R.id.details);


            if (details == null || details.getShownIndex() != index) {

                // Make the details fragment and give it the currently selected hero index
                details = DetailsFragment.newInstance(index);

                // Start Fragment transactions
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                // Replace any other Fragment with our new Details Fragment with the right data
                ft.replace(R.id.details, details);

                // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else { // show as activity

            // Launch a new Activity to show our DetailsFragment
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }
}