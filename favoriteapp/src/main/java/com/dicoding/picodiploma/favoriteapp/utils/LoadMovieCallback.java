package com.dicoding.picodiploma.favoriteapp.utils;

import com.dicoding.picodiploma.favoriteapp.model.Movie;

import java.util.ArrayList;

public interface LoadMovieCallback {
    void preExecute();

    void postExecute(ArrayList<Movie> movies);
}
