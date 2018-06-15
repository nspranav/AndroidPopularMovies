package com.example.nspra.popularmovies;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nspra.popularmovies.model.MovieDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nspra on 5/24/2018.
 */

public class MovieAdapter  extends ArrayAdapter<MovieDetail>{


    private List<MovieDetail> movieDetails = new ArrayList<>();
    private Context mContext;

    public MovieAdapter(Context c){

        super(c,0);
        mContext=c;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d("get view calles", "getView: "+getContext());
        MovieDetail movieDetail = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.mMoviePoster = (ImageView) convertView.findViewById(R.id.iv_poster);
            viewHolder.mPosterText = (TextView) convertView.findViewById(R.id.tv_posterText);
            viewHolder.mRatingBar = (RatingBar) convertView.findViewById(R.id.rb_rating_bar);
            convertView.setTag(viewHolder); // view lookup cache stored in tag
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.mPosterText.setText(movieDetail.getUserRating());
        Log.d("getview", "getView: user rating"+ movieDetail.getUserRating());
        viewHolder.mRatingBar.setRating(Float.parseFloat(movieDetail.getUserRating())/2.0f);
        //viewHolder.mMoviePoster.setLayoutParams(new LinearLayout.LayoutParams(185, 285));
        //viewHolder.mMoviePoster.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //viewHolder.mMoviePoster.setPadding(8, 8, 8, 8);

        Picasso.with(getContext()).load(movieDetail.getCompletePosterPath()).into(viewHolder.mMoviePoster);

        // Return the completed view to render on screen
        return convertView;
    }

    public void setMovieDetailData(List<MovieDetail> movieDetailList) {

        this.movieDetails = movieDetailList;
        notifyDataSetChanged();
        Log.d("setMovieDetails", "setMovieDetailData: "+movieDetails.size());
        Log.d("setMovieDetails", "setMovieDetailData: "+getContext());
    }

    @Override
    public int getCount() {
        return movieDetails.size();
    }

    @Nullable
    @Override
    public MovieDetail getItem(int position) {
        return movieDetails.get(position);
    }

    private static class ViewHolder {
        ImageView mMoviePoster;
        RatingBar mRatingBar;
        TextView mPosterText;

    }
}
