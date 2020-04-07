package com.dicoding.picodiploma.moviecataloguelastproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static androidx.constraintlayout.widget.Constraints.TAG;
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
