package com.example.newsgateway;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SourceAdapter extends ArrayAdapter<Source> {

    private ViewHolder viewHolder;

    public static class ViewHolder{
        private TextView sourceTextView;
    }

    public SourceAdapter(
        @NonNull Context context,
        @LayoutRes int res,
        @NonNull ArrayList<Source> sourceList){
        super(context, res, sourceList);
    }

    @NonNull
    @Override
    public View getView(
        int pos,
        @Nullable View convertView,
        @NonNull ViewGroup parent
    ){
        if(convertView == null){
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.drawer_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.sourceTextView = (TextView) convertView.findViewById(R.id.sourceTextView);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.sourceTextView.setText(getItem(pos).getName());
        return convertView;
    }
}
