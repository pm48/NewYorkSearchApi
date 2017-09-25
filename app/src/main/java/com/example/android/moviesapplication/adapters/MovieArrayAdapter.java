package com.example.android.moviesapplication.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviesapplication.R;
import com.example.android.moviesapplication.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by prernamanaktala on 9/15/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    // View lookup cache
    private static class ViewHolder {
        ImageView imgView;
        TextView title;
        TextView summary;
    }


    public MovieArrayAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie,parent,false);
            viewHolder.imgView = (ImageView) convertView.findViewById(R.id.idImage);
            viewHolder.imgView.setImageResource(0);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.summary = (TextView) convertView.findViewById(R.id.tvOverview);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath()).into(viewHolder.imgView);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movie.getBackdrop_path()).into(viewHolder.imgView);
        }

        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.summary.setText(movie.getOverview());
        return convertView;

    }
}
