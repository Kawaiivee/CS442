package com.christopherhield.geography;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryFragment extends Fragment {


    public CountryFragment() {
        // Required empty public constructor
    }


    public static CountryFragment newInstance(Country country, int index, int max)
    {
        CountryFragment f = new CountryFragment();
        Bundle bdl = new Bundle(1);
        bdl.putSerializable("COUNTRY_DATA", country);
        bdl.putSerializable("INDEX", index);
        bdl.putSerializable("TOTAL_COUNT", max);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment_layout = inflater.inflate(R.layout.fragment_country, container, false);


        final Country currentCountry = (Country) getArguments().getSerializable("COUNTRY_DATA");
        int index = getArguments().getInt("INDEX");
        int total = getArguments().getInt("TOTAL_COUNT");

        TextView country = fragment_layout.findViewById(R.id.country);
        country.setText(currentCountry.getName());

        TextView region = fragment_layout.findViewById(R.id.region);
        region.setText(currentCountry.getRegion() +
                " (" + currentCountry.getSubRegion() + ")");

        TextView capital = fragment_layout.findViewById(R.id.capital);
        capital.setText(currentCountry.getCapital());

        TextView population = fragment_layout.findViewById(R.id.population);
        population.setText(String.format(Locale.US, "%,d", currentCountry.getPopulation()));

        TextView area = fragment_layout.findViewById(R.id.area);
        area.setText(String.format(Locale.US,"%,d sq km", currentCountry.getArea()));

        TextView citizen = fragment_layout.findViewById(R.id.citizens);
        citizen.setText(currentCountry.getCitizen());

        TextView codes = fragment_layout.findViewById(R.id.codes);
        codes.setText(currentCountry.getCallingCodes());

        TextView borders = fragment_layout.findViewById(R.id.borders);
        borders.setText(currentCountry.getBorders());

        TextView pageNum = fragment_layout.findViewById(R.id.page_num);
        pageNum.setText(String.format(Locale.US, "%d of %d", index, total));

        ImageView imageView = fragment_layout.findViewById(R.id.imageView);
        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        imageView.setImageDrawable(currentCountry.getDrawable());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFlag(currentCountry.getName());
            }
        });
        return fragment_layout;
    }



    public void clickFlag(String name) {

        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(name));

        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);

    }


}
