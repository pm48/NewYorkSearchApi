package com.example.android.moviesapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviesapplication.models.Movie;
import com.squareup.picasso.Picasso;

import static com.example.android.moviesapplication.MovieActivity.EXTRA_PARAMS;

/**
 * Created by prernamanaktala on 9/17/17.
 */

public class MovieItemActivity extends AppCompatActivity {
        ImageView imgView;
        TextView title;
        TextView summary;
        TextView ratings;
        TextView popularity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_movie);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_PARAMS)) {
            Movie movie = intent.getExtras().getParcelable(EXTRA_PARAMS);
            title = (TextView) findViewById(R.id.tvTitle);
            summary = (TextView) findViewById(R.id.tvOverview);
            ratings = (TextView) findViewById(R.id.tvRatings);
            popularity = (TextView) findViewById(R.id.tvPopularity);
            imgView = (ImageView) findViewById(R.id.idImage);
            title.setText(movie.getOriginalTitle());
            summary.setText(movie.getOverview());
            ratings.setText(String.valueOf(movie.getRatings()));
            popularity.setText(String.valueOf(movie.getPopularity()));

            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Picasso.with(this).load(movie.getPosterPath()).into(imgView);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Picasso.with(this).load(movie.getBackdrop_path()).into(imgView);
            }


        }

    }
}
