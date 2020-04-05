package com.dicoding.picodiploma.favoriteapp.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.picodiploma.favoriteapp.R;
import com.dicoding.picodiploma.favoriteapp.adapter.FavoriteMovieAdapter;
import com.dicoding.picodiploma.favoriteapp.database.DatabaseContract;
import com.dicoding.picodiploma.favoriteapp.mappinghelper.MappingMovieHelper;
import com.dicoding.picodiploma.favoriteapp.model.Movie;
import com.dicoding.picodiploma.favoriteapp.utils.LoadMovieCallback;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;


public class MovieFavoriteFragment extends Fragment implements LoadMovieCallback {
    private ProgressBar progressBar;
    private RecyclerView rvFavMovie;
    private FavoriteMovieAdapter favoriteMovieAdapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";


    public MovieFavoriteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.movie_favorite_progressbar);
        rvFavMovie = view.findViewById(R.id.rv_movie_favorite);
        rvFavMovie.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvFavMovie.setHasFixedSize(true);
        favoriteMovieAdapter = new FavoriteMovieAdapter(getActivity());
        rvFavMovie.setAdapter(favoriteMovieAdapter);


        if (savedInstanceState == null) {
            new LoadMoviesAsync(getContext(), this).execute();
        } else {
            ArrayList<Movie> arrayList = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (arrayList != null) {
                favoriteMovieAdapter.setListMovies(arrayList);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, favoriteMovieAdapter.getListMovies());
    }

    @Override
    public void preExecute() {
        new Thread(() -> progressBar.setVisibility(View.VISIBLE));
    }

    @Override
    public void postExecute(ArrayList<Movie> movie) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movie.size() > 0) {
            favoriteMovieAdapter.setListMovies(movie);
        } else {
            favoriteMovieAdapter.setListMovies(new ArrayList<>());
            showSnackbarMessage(getString(R.string.empty_movie_favorite));
        }
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMoviesAsync(Context context, LoadMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor movieCursor = context.getContentResolver().query(DatabaseContract.FavoriteMovie.CONTENT_URI, null, null, null, null);
            return MappingMovieHelper.mapCursorToArrayList(Objects.requireNonNull(movieCursor));
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movie) {
            super.onPostExecute(movie);
            weakCallback.get().postExecute(movie);

        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFavMovie, message, Snackbar.LENGTH_SHORT).show();
    }
}