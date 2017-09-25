package com.example.android.moviesapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prernamanaktala on 9/15/17.
 */

public class Movie implements Parcelable{
    String posterPath;
    String originalTitle;
    String overview;
    String backdrop_path;
    Double popularity;
    Double ratings;




    protected Movie(Parcel in) {
        posterPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        backdrop_path = in.readString();
        popularity = in.readDouble();
        ratings = in.readDouble();
    }



    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Double getPopularity() {
        return popularity;
    }

    public Double getRatings() {
        return ratings;
    }

    public String getPosterPath() {

        return "https://image.tmdb.org/t/p/w342/" + posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getBackdrop_path() {
        return "https://image.tmdb.org/t/p/w342/" + backdrop_path;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdrop_path = jsonObject.getString("backdrop_path");
        this.ratings = jsonObject.getDouble("vote_average");
        this.popularity = jsonObject.getDouble("popularity");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array)  {
        ArrayList<Movie> results = new ArrayList<>();
        for (int x=0;x< array.length();x++){
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(backdrop_path);
        dest.writeDouble(ratings);
        dest.writeDouble(popularity);
    }
}
