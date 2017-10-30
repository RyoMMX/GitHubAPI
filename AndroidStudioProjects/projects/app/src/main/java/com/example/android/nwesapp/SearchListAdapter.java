package com.example.android.nwesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchListAdapter extends ArrayAdapter<Result> {
    private int lastPosition;

    public SearchListAdapter(@NonNull Context context, @NonNull ArrayList<Result> objects) {
        super(context, 0, objects);
        lastPosition = objects.size() - 1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.result_list_view, parent, false);
        }
        Result result = getItem(position);

        TextView webTitle = convertView.findViewById(R.id.webTitle);
        ImageView thumbnail = convertView.findViewById(R.id.thumbnail);
        TextView webPublicationDate = convertView.findViewById(R.id.webPublicationDate);
        TextView byline = convertView.findViewById(R.id.byline);

        webTitle.setText(result.getWebTitle().toString());
        webPublicationDate.setText(result.getWebPublicationDate().toString());
        byline.setText(result.getByline().toString());

        Picasso.with(getContext()).load(result.getThumbnail()).into(thumbnail);

        return convertView;
    }
}
