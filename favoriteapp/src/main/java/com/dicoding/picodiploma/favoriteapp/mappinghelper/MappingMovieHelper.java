package com.dicoding.picodiploma.favoriteapp.mappinghelper;

import android.database.Cursor;

import com.dicoding.picodiploma.favoriteapp.database.DatabaseContract;
import com.dicoding.picodiploma.favoriteapp.model.Movie;

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
}
