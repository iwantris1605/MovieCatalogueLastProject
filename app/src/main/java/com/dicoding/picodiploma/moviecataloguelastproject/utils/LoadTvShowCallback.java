package com.dicoding.picodiploma.moviecataloguelastproject.utils;

import com.dicoding.picodiploma.moviecataloguelastproject.model.TvShow;

import java.util.ArrayList;

public interface LoadTvShowCallback {
    void preExecute();

    void postExecute(ArrayList<TvShow> tvShows);
}
