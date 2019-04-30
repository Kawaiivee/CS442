package com.christopherhield.emptypagertest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    public static final MyFragment newInstance(String message)
    {
        MyFragment f = new MyFragment();

        //Constructs a new, empty Bundle sized to hold the given number of elements. (1)
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String message = getArguments().getString(EXTRA_MESSAGE);

        // Get the string to display from the arguments bundle
        View v = inflater.inflate(R.layout.myfragment_layout, container, false);

        TextView messageTextView = v.findViewById(R.id.textView); // Get reference to text view 1
        messageTextView.setText(message); // Set the text to the message from the bundle

        TextView textView2 = v.findViewById(R.id.textView2); // Get reference to text view 2
        textView2.setText("Lucky Number: " + (int) (Math.random() * 1000)); // Set to random string

        return v;
    }

}