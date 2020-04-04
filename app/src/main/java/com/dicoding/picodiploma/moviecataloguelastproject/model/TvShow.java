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

public class TvShow implements Parcelable {
    private int id;
    private String poster_path;
    private String name;
    private String first_air_date;
    private String overview;

    public TvShow (int id, String name, String first_air_date, String overview, String poster_path){
        this.id = id;
        this.name = name;
        this.first_air_date = first_air_date;
        this.overview = overview;
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
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

    public TvShow (Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.name = getColumnString(cursor, DatabaseContract.FavoriteTvShow.NAME);
        this.first_air_date = getColumnString(cursor, DatabaseContract.FavoriteTvShow.FIRST_AIR_DATE);
        this.overview = getColumnString(cursor, DatabaseContract.FavoriteTvShow.OVERVIEW);
        this.poster_path = getColumnString(cursor, DatabaseContract.FavoriteTvShow.POSTER_PATH);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster_path);
        dest.writeString(this.name);
        dest.writeString(this.first_air_date);
        dest.writeString(this.overview);
    }

    private TvShow(Parcel in) {
        this.id = in.readInt();
        this.poster_path = in.readString();
        this.name = in.readString();
        this.first_air_date = in.readString();
        this.overview = in.readString();
    }

    public TvShow() {

    }

    public TvShow(JSONObject jsonObject) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd, mm, yyyy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            int id = jsonObject.getInt("id");
            String poster_path = jsonObject.getString("poster_path");
            String name = jsonObject.getString("name");
            String first_air_date = jsonObject.getString("first_air_date");
            String overview = jsonObject.getString("overview");

            this.id = id;
            this.poster_path = poster_path;
            this.name = name;
            this.first_air_date = formatter.format(Objects.requireNonNull(dateFormat.parse(first_air_date)));
            this.overview = overview;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

}
