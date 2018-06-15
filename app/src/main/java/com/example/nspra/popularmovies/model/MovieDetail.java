package com.example.nspra.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ShareCompat;
import android.util.Log;

/**
 * Created by nspra on 5/21/2018.
 */

public class MovieDetail implements Parcelable {
    private static  final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185/";

    private String title;
    private String thumbNailPath;
    private String overview;
    private String userRating;
    private String releaseDate;
    private String completePosterPath;

    public MovieDetail(){

    }

    public MovieDetail(String title,String thumbNail, String overview,String userRating,String releaseDate) {
        setTitle(title);
        setThumbNailPath(thumbNail);
        setOverview(overview);
        setReleaseDate(releaseDate);
        setUserRating(userRating);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbNailPath() {
        return thumbNailPath;
    }

    public void setThumbNailPath(String thumbNailPath) {
        this.thumbNailPath = thumbNailPath;
        this.completePosterPath = BASE_IMAGE_URL+IMAGE_SIZE+thumbNailPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCompletePosterPath(){
        Log.d("getCompletePosterPath", "called as");
        return completePosterPath; }


    //The below methods are implemented for the purpose of creating Parcels

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.getTitle());
        dest.writeString(this.getThumbNailPath());
        dest.writeString(this.getOverview());
        dest.writeString(this.getUserRating());
        dest.writeString(this.getReleaseDate());
    }

    public static final Parcelable.Creator<MovieDetail> CREATOR = new Parcelable.Creator<MovieDetail>(){
      public MovieDetail createFromParcel(Parcel in){
          return new MovieDetail(in);
      }

      public MovieDetail[] newArray(int size){
          return new  MovieDetail[size];
      }
    };


    //constructor to handle the parcelable
    private MovieDetail(Parcel in){
        this.setTitle(in.readString());
        this.setThumbNailPath(in.readString());
        this.setOverview(in.readString());
        this.setUserRating(in.readString());
        this.setReleaseDate(in.readString());
    }
}
