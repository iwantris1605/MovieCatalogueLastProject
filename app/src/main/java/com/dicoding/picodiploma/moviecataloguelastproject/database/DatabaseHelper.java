package com.dicoding.picodiploma.moviecataloguelastproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmoviecatalogue";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s INTEGER," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.FavoriteMovie.TABLE_MOVIE,
            DatabaseContract.FavoriteMovie._ID,
            DatabaseContract.FavoriteMovie.MOVIE_ID,
            DatabaseContract.FavoriteMovie.TITLE,
            DatabaseContract.FavoriteMovie.RELEASE_DATE,
            DatabaseContract.FavoriteMovie.OVERVIEW,
            DatabaseContract.FavoriteMovie.PHOTO
    );

    private static final String SQL_CREATE_TABLE_TV_SHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s INTEGER," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.FavoriteTvShow.TABLE_TV,
            DatabaseContract.FavoriteTvShow._ID,
            DatabaseContract.FavoriteTvShow.TV_SHOW_ID,
            DatabaseContract.FavoriteTvShow.NAME,
            DatabaseContract.FavoriteTvShow.FIRST_AIR_DATE,
            DatabaseContract.FavoriteTvShow.OVERVIEW,
            DatabaseContract.FavoriteTvShow.POSTER_PATH
    );


    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_MOVIE);
        database.execSQL(SQL_CREATE_TABLE_TV_SHOW);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavoriteMovie.TABLE_MOVIE);
        database.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavoriteTvShow.TABLE_TV);
        onCreate(database);
    }
}
