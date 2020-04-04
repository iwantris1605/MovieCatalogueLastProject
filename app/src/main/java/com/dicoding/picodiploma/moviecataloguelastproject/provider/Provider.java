package com.dicoding.picodiploma.moviecataloguelastproject.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dicoding.picodiploma.moviecataloguelastproject.database.MovieHelper;
import com.dicoding.picodiploma.moviecataloguelastproject.database.TvShowHelper;

import java.util.Objects;

import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.AUTHORITY;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.CONTENT_URI;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.TABLE_MOVIE;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteTvShow.CONTENT_URI_TV;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteTvShow.TABLE_TV;

public class Provider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TV = 10;
    private static final int TV_ID = 20;
    private MovieHelper movieHelper;
    private TvShowHelper tvShowHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);

        sUriMatcher.addURI(AUTHORITY,
                TABLE_MOVIE + "/#",
                MOVIE_ID);
    }

    static {

        sUriMatcher.addURI(AUTHORITY, TABLE_TV, TV);

        sUriMatcher.addURI(AUTHORITY,
                TABLE_TV + "/#",
                TV_ID);
    }

    public Provider() {
    }

    @Override
    public boolean onCreate() {
            movieHelper = MovieHelper.getInstance(getContext());
            movieHelper.open();
            tvShowHelper = TvShowHelper.getInstance(getContext());
            tvShowHelper.open();
            return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryAll();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryById(uri.getLastPathSegment());
                break;
            case TV:
                cursor = tvShowHelper.queryAll();
                break;
            case TV_ID:
                cursor = tvShowHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null){
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;
        Uri contentUri = null;

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insert(contentValues);
                if (added > 0) {
                    contentUri = ContentUris.withAppendedId(CONTENT_URI, added);
                }
                break;
            case TV:
                added = tvShowHelper.insert(contentValues);
                if (added > 0) {
                    contentUri = ContentUris.withAppendedId(CONTENT_URI_TV, added);
                }
                break;
            default:
                added = 0;
                break;
        }
        if (added > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return contentUri;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
            updated = movieHelper.update(uri.getLastPathSegment(), contentValues);
            break;
            case TV_ID:
                updated = tvShowHelper.update(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        if (updated > 0 ) {

            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
            deleted = movieHelper.deleteById(uri.getLastPathSegment());
            break;
            case TV_ID:
                deleted = tvShowHelper.deleteById(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }
}
