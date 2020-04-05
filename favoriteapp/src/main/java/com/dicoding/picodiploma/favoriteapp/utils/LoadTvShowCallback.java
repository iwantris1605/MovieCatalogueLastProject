package com.dicoding.picodiploma.favoriteapp.utils;

import com.dicoding.picodiploma.favoriteapp.model.TvShow;

import java.util.ArrayList;

public interface LoadTvShowCallback {
    void preExecute();

    void postExecute(ArrayList<TvShow> tvShows);
}
