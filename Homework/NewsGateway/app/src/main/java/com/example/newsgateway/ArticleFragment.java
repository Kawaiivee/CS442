package com.example.newsgateway;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ArticleFragment extends Fragment implements Serializable {

    //Create an instance of the "ArticleFragment" class
    public static final ArticleFragment newInstance(Article A){
        ArticleFragment F = new ArticleFragment();
        Bundle B = new Bundle(1);
        B.putSerializable("article", A);
        F.setArguments(B);
        return F;
    }

    @Nullable
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        @Nullable ViewGroup container,
        Bundle savedInstanceState
            ){
        final Article A;
        if(savedInstanceState == null){
            A = (Article) getArguments().getSerializable("article");
        }
        else{
            A = (Article) savedInstanceState.getSerializable("article");
        }
        View V = inflater.inflate(R.layout.article_fragment, container, false);

        TextView titleView = (TextView) V.findViewById(R.id.titleView);
        TextView authorView = (TextView) V.findViewById(R.id.authorView);
        TextView dateView = (TextView) V.findViewById(R.id.dateView);
        TextView descriptionView = (TextView) V.findViewById(R.id.descriptionView);
        TextView indexView = (TextView) V.findViewById(R.id.indexView);
        final ImageButton imageButton = (ImageButton) V.findViewById(R.id.imageView); //this need to be final bc in an inner class

        titleView.setText(A.getTitle());
        authorView.setText(A.getAuthor());
        dateView.setText(A.getPublished().split("T")[0]);
        descriptionView.setText(A.getDescription());
        indexView.setText("Article #"+A.getIndex()+" of " + A.getTotal());

        if (A.getUrlToImage() != null){
            Picasso picasso = new Picasso.Builder(V.getContext()).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
                    final String changedUrl = A.getUrlToImage().replace("http:", "https:");
                    picasso.load(changedUrl) .error(R.drawable.broken)
                            .placeholder(R.drawable.placeholder).into(imageButton);
                }
            }).build();

            picasso.load(A.getUrlToImage()).error(R.drawable.broken)
                    .placeholder(R.drawable.placeholder).into(imageButton);
        } else {
            Log.d("No Image", "Some BS");
        }

        final Intent i = new Intent((Intent.ACTION_VIEW));
        i.setData(Uri.parse(A.getUrl()));
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        descriptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        return V;
    }
}
