package com.dicoding.picodiploma.favoriteapp.mappinghelper;

import android.database.Cursor;

import com.dicoding.picodiploma.favoriteapp.database.DatabaseContract;
import com.dicoding.picodiploma.favoriteapp.model.TvShow;

import java.util.ArrayList;

public class MappingTvShowHelper {
    public static ArrayList<TvShow> mapCursorToArrayList(Cursor tvCursor) {
        ArrayList<TvShow> tvList = new ArrayList<>();

        while (tvCursor.moveToNext()) {
            int id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.TV_SHOW_ID));
            String name = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.NAME));
            String first_air_date = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.FIRST_AIR_DATE));
            String overview = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.OVERVIEW));
            String poster_path = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.POSTER_PATH));
            tvList.add(new TvShow(id, name, first_air_date, overview, poster_path));
        }
        return tvList;
    }

    public static TvShow mapCursorToObject(Cursor tvCursor) {
        tvCursor.moveToFirst();
        int id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.TV_SHOW_ID));
        String name = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.NAME));
        String first_air_date = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.FIRST_AIR_DATE));
        String overview = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.OVERVIEW));
        String poster_path = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.POSTER_PATH));
        return new TvShow(id, name, first_air_date, overview, poster_path);
    }

}
