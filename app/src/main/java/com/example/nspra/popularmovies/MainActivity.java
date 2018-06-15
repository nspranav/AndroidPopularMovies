package com.example.nspra.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nspra.popularmovies.model.MovieDetail;
import com.example.nspra.popularmovies.utilities.JsonUtils;
import com.example.nspra.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TextView mTextDisplay;
    private ProgressBar mProgressBar;
    private Button mRetryButton;
    private GridView mMovieGridView;
    private String currentPreference="top_rated";

    private MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextDisplay = findViewById(R.id.tv_error_message);

        mProgressBar = findViewById(R.id.pb_loading_indicator);

        mRetryButton = findViewById(R.id.button_retry);

        mMovieGridView = findViewById(R.id.gv_movie_details);

        movieAdapter= new MovieAdapter(MainActivity.this);


        mMovieGridView.setAdapter(movieAdapter);

        loadMovieData(currentPreference);

        mMovieGridView.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemSelected = item.getItemId();

        switch (itemSelected){
            case R.id.most_popular:
                currentPreference = "popular";
                loadMovieData(currentPreference);
                break;
            case R.id.top_rated:
                currentPreference = "top_rated";
                loadMovieData(currentPreference);
                break;
        }
        return true;
    }

    //This method listens for any clicks on the grid view.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        MovieDetail movieClicked = movieAdapter.getItem(position);

        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("myMovieDetail",movieClicked);
        startActivity(intent);

    }

    private void loadMovieData(String preference){
        new FetchMovieDataTask().execute(preference);
    }

    public void onRetryButtonClick(View view) {
        loadMovieData(currentPreference);
    }

    private void showErrorAndRetry(){
        mTextDisplay.setVisibility(View.VISIBLE);
        mRetryButton.setVisibility(View.VISIBLE);
    }

    private void hideErrorAndRetry(){
        mTextDisplay.setVisibility(View.INVISIBLE);
        mRetryButton.setVisibility(View.INVISIBLE);
    }

    public class FetchMovieDataTask extends AsyncTask<String,Void,List<MovieDetail>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<MovieDetail> doInBackground(String... params) {
            if(params.length == 0 ){
                return null;
            }

            String preference = params[0];

            URL movieDataUrl = NetworkUtils.buildUrl(MainActivity.this,preference);

            if (isOnline()) {
                try {
                    String jsonMovieData = NetworkUtils.getResponseFromHttpUrl(movieDataUrl);

                    return JsonUtils.parseMovieDetailJson(jsonMovieData);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }else{
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<MovieDetail> movieDetails) {
            mProgressBar.setVisibility(View.INVISIBLE);
            hideErrorAndRetry();
            if(movieDetails != null){
                mMovieGridView.setVisibility(View.VISIBLE);
                movieAdapter.setMovieDetailData(movieDetails);
                //Log.d("main actibvity", "onPostExecute: working");
                //mTextDisplay.setText(movieDetails.get(2).getTitle()+"");
            }else{
                showErrorAndRetry();
                mTextDisplay.setText("Error occured! Check your network connection");

            }
        }

        private boolean isOnline() {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
    }

}
