package com.example.nspra.popularmovies.utilities;

import com.example.nspra.popularmovies.model.MovieDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nspra on 5/21/2018.
 */

public class JsonUtils {
    public static List<MovieDetail> parseMovieDetailJson(String json) {
        List<MovieDetail> movieDetail = new ArrayList<MovieDetail>();

        try {
            JSONObject movieDetailJson = new JSONObject(json);
            List<JSONObject> result = getJsonObjectList(movieDetailJson.getJSONArray("results"));

            for (JSONObject movieObject: result) {
                String title,thumbnailPath,overview,userRating,releaseDate;

                title = movieObject.optString("title","no-title");
                thumbnailPath = movieObject.optString("poster_path","no-thumbnail");
                overview = movieObject.optString("overview","no-overview");
                userRating = movieObject.optString("vote_average","no-rating");
                releaseDate = movieObject.optString("release_date","no-release-date");

                movieDetail.add(new MovieDetail(title,thumbnailPath,overview,userRating,releaseDate));
            }
            return movieDetail;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getList(JSONArray array){
        List<String> values = new ArrayList<>();

        if(array!=null){
            try{
                for (int i=0;i<array.length();i++){
                    values.add(array.getString(i));
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return values;
    }

    private static List<JSONObject> getJsonObjectList(JSONArray array){
        List<JSONObject> values = new ArrayList<>();

        if(array!=null){
            try{
                for (int i=0;i<array.length();i++){
                    values.add(array.getJSONObject(i));
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return values;
    }


}
