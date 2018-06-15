package com.example.nspra.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nspra.popularmovies.model.MovieDetail;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private ImageView mPosterImageView;
    private TextView mTitleTextView;
    private TextView mReleaseDate;
    private TextView mRating;
    private TextView mOverview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPosterImageView = (ImageView) findViewById(R.id.image_iv);
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mRating = (TextView) findViewById(R.id.tv_user_rating);
        mOverview = (TextView) findViewById(R.id.tv_overview);

        Bundle data = getIntent().getExtras();
        MovieDetail movieDetail = data.getParcelable("myMovieDetail");
        mPosterImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(this).load(movieDetail.getCompletePosterPath()).into(mPosterImageView);
        mTitleTextView.setText(movieDetail.getTitle());
        mReleaseDate.setText(movieDetail.getReleaseDate());
        mRating.setText(movieDetail.getUserRating());
        mOverview.setText(movieDetail.getOverview());
    }
}
