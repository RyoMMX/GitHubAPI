package com.example.android.githubapi;

import android.content.Context;
import android.support.annotation.LayoutRes;
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

/**
 * Created by muhammad on 10/23/2017.
 */

public class RepositorySearchListAdapter extends ArrayAdapter<Item> {
    private int resource;

    public RepositorySearchListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Item> data) {
        super(context, resource, data);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ImageView avatar_image;
        TextView name;
        TextView language;
        TextView description;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        Item item = getItem(position);

        avatar_image = convertView.findViewById(R.id.avatar_image);
        name = convertView.findViewById(R.id.name);
        language = convertView.findViewById(R.id.language);
        description = convertView.findViewById(R.id.description);

        Picasso.with(getContext()).load(item.getOwner().getAvatar_url()).into(avatar_image);
        name.setText(item.getName());
        language.setText(item.getLanguage());
        description.setText(item.getDescription());

        return convertView;
    }
}
