package com.dicoding.picodiploma.moviecataloguelastproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dicoding.picodiploma.moviecataloguelastproject.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.MOVIE_ID;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.OVERVIEW;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.PHOTO;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.RELEASE_DATE;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.TABLE_MOVIE;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.TITLE;

public class MovieHelper {

    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getFavoriteMovie() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        database = dataBaseHelper.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE,
                new String[]{_ID, TITLE, RELEASE_DATE, OVERVIEW, PHOTO},
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie._ID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.TITLE)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.RELEASE_DATE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.OVERVIEW)));
                movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovie.PHOTO)));

                arrayList.add(movie);

                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public boolean checkFavoriteMovie(String id) {
        database = dataBaseHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_MOVIE + " WHERE " + DatabaseContract.FavoriteMovie.MOVIE_ID + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{id});
        boolean checkFavoriteMovie = false;
        if (cursor.moveToFirst()) {
            checkFavoriteMovie = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkFavoriteMovie;
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, MOVIE_ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, MOVIE_ID + " = ?", new String[]{id});
    }
}
