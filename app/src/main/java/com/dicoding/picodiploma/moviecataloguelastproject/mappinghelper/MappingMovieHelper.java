package com.dicoding.picodiploma.moviecataloguelastproject.mappinghelper;

import android.database.Cursor;

import com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract;
import com.dicoding.picodiploma.moviecataloguelastproject.model.Movie;

import java.util.ArrayList;

public class MappingMovieHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor movieCursor) {
        ArrayList<Movie> movieList = new ArrayList<>();

        while (movieCursor.moveToNext()) {
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.MOVIE_ID));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.TITLE));
            String release_date = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.RELEASE_DATE));
            String overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.OVERVIEW));
            String photo = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.PHOTO));
            movieList.add(new Movie(id, title, release_date, overview, photo));
        }
        return movieList;
    }

    public static Movie mapCursorToObject(Cursor movieCursor) {
        movieCursor.moveToFirst();
        int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.MOVIE_ID));
        String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.TITLE));
        String release_date = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.RELEASE_DATE));
        String overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.OVERVIEW));
        String photo = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.PHOTO));
        return new Movie(id, title, release_date, overview, photo);
    }

}
