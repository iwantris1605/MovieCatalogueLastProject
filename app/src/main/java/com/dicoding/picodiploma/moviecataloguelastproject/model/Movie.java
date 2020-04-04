package com.dicoding.picodiploma.moviecataloguelastproject.model;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Objects;

import static android.provider.BaseColumns._ID;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.getColumnInt;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.getColumnString;


public class Movie implements Parcelable {
    private int id;
    private String photo;
    private String title;
    private String release_date;
    private String overview;

    public Movie(int id, String title, String release_date, String overview, String photo){
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.overview = overview;
        this.photo = photo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public Movie(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.FavoriteMovie.TITLE);
        this.release_date = getColumnString(cursor, DatabaseContract.FavoriteMovie.RELEASE_DATE);
        this.overview = getColumnString(cursor, DatabaseContract.FavoriteMovie.OVERVIEW);
        this.photo = getColumnString(cursor, DatabaseContract.FavoriteMovie.PHOTO);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.photo);
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
    }

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.photo = in.readString();
        this.title = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
    }

    public Movie() {
    }

    public Movie(JSONObject jsonObject) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd, mm, yyyy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            int id=jsonObject.getInt("id");
            String poster_path = jsonObject.getString("poster_path");
            String title = jsonObject.getString("title");
            String release_date = jsonObject.getString("release_date");
            String overview = jsonObject.getString("overview");

            this.id = id;
            this.photo = poster_path;
            this.title = title;
            this.release_date = formatter.format(Objects.requireNonNull(dateFormat.parse(release_date)));
            this.overview = overview;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
