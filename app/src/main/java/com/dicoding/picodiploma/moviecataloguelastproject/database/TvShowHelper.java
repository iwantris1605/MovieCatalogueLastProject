package com.dicoding.picodiploma.moviecataloguelastproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dicoding.picodiploma.moviecataloguelastproject.model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteTvShow.FIRST_AIR_DATE;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteTvShow.NAME;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteTvShow.OVERVIEW;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteTvShow.POSTER_PATH;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteTvShow.TABLE_TV;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteTvShow.TV_SHOW_ID;


public class TvShowHelper {
    private static final String DATABASE_TABLE = TABLE_TV;
    private static DatabaseHelper dataBaseHelper;
    private static TvShowHelper INSTANCE;

    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
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

    public ArrayList<TvShow> getTvShow() {
        ArrayList<TvShow> arrayListTv = new ArrayList<>();
        database = dataBaseHelper.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE,
                new String[]{_ID, NAME, FIRST_AIR_DATE, OVERVIEW, POSTER_PATH},
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(_ID))));
                tvShow.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.NAME)));
                tvShow.setFirst_air_date(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.FIRST_AIR_DATE)));
                tvShow.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.OVERVIEW)));
                tvShow.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShow.POSTER_PATH)));

                arrayListTv.add(tvShow);

                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayListTv;
    }

    public long insertFavoriteTvShow(TvShow tvShow) {
        ContentValues values = new ContentValues();
        values.put(TV_SHOW_ID, tvShow.getId());
        values.put(NAME, tvShow.getName());
        values.put(FIRST_AIR_DATE, tvShow.getFirst_air_date());
        values.put(OVERVIEW, tvShow.getOverview());
        values.put(POSTER_PATH, tvShow.getPoster_path());

        return database.insert(DATABASE_TABLE, null, values);
    }

    public void deleteFavoriteTvShow(int id) {
        database = dataBaseHelper.getWritableDatabase();
        database.delete(DatabaseContract.FavoriteTvShow.TABLE_TV, DatabaseContract.FavoriteTvShow.TV_SHOW_ID + "=" + id, null);
    }

    public boolean checkFavoriteTvShow(String id) {
        database = dataBaseHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + DatabaseContract.FavoriteTvShow.TABLE_TV + " WHERE " + DatabaseContract.FavoriteTvShow.TV_SHOW_ID + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{id});
        boolean checkFavoriteTvShow = false;
        if (cursor.moveToFirst()) {
            checkFavoriteTvShow = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkFavoriteTvShow;
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
        return database.update(DATABASE_TABLE, values, TV_SHOW_ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, TV_SHOW_ID + " = ?", new String[]{id});
    }
}
