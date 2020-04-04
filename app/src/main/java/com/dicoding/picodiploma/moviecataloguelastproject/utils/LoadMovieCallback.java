package com.dicoding.picodiploma.moviecataloguelastproject.utils;

import com.dicoding.picodiploma.moviecataloguelastproject.model.Movie;

import java.util.ArrayList;

public interface LoadMovieCallback {
    void preExecute();

    void postExecute(ArrayList<Movie> movies);
}
