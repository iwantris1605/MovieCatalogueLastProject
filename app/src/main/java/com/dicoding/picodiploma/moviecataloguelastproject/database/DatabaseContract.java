package com.dicoding.picodiploma.moviecataloguelastproject.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.dicoding.picodiploma.moviecataloguelastproject";
    private static final String SCHEME = "content";

    public static final class FavoriteMovie implements BaseColumns {

        public static final String TABLE_MOVIE = "movie_favorite";
        public static final String MOVIE_ID = "movie_id";
        public static final String TITLE = "title";
        public static final String RELEASE_DATE = "release_date";
        public static final String OVERVIEW = "overview";
        public static final String PHOTO = "poster_path";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    public static final class FavoriteTvShow implements BaseColumns {

        public static final String TABLE_TV = "tv_favorite";
        public static final String TV_SHOW_ID = "tv_id";
        public static final String NAME = "name";
        public static final String FIRST_AIR_DATE = "first_air_date";
        public static final String OVERVIEW = "overview";
        public static final String POSTER_PATH = "poster_path";

        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
