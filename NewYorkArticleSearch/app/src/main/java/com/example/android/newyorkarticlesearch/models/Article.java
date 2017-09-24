package com.example.android.newyorkarticlesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prernamanaktala on 9/21/17.
 */

public class Article implements Parcelable {
    String webUrl;
    String headline;
    String thumbNail;

    public Article (JSONObject jsonObject){
        try {
            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");
            JSONArray multimedia = jsonObject.getJSONArray("multimedia");

            if(multimedia.length() > 0){
                JSONObject multimediaJSON = multimedia.getJSONObject(0);
                this.thumbNail = "http://www.nytimes.com//" + multimediaJSON.getString("url");
            }
            else{
                this.thumbNail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected Article(Parcel in) {
        webUrl = in.readString();
        headline = in.readString();
        thumbNail = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getHeadline() {
        return headline;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public static List<Article> fromJSONArray(JSONArray array){
        List<Article> results = new ArrayList<>();

        for (int x=0; x < array.length();x++){
            try{
                    results.add(new Article(array.getJSONObject(x)));
            }
            catch(JSONException ex){
                ex.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(webUrl);
        parcel.writeString(headline);
        parcel.writeString(thumbNail);
    }
}
