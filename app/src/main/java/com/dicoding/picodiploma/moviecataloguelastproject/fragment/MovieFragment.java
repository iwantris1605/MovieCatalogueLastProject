package com.dicoding.picodiploma.moviecataloguelastproject.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.picodiploma.moviecataloguelastproject.R;
import com.dicoding.picodiploma.moviecataloguelastproject.adapter.CardViewMovieAdapter;
import com.dicoding.picodiploma.moviecataloguelastproject.model.Movie;
import com.dicoding.picodiploma.moviecataloguelastproject.viewModel.MovieViewModel;

import java.util.ArrayList;
import java.util.Objects;


public class MovieFragment extends Fragment {
    private MovieViewModel movieViewModel;
    private CardViewMovieAdapter cardViewMovieAdapter;
    private RecyclerView rvMovies;
    private ProgressBar progressBar;


    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovies = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.movie_progressBar);

        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.setMovie();
        movieViewModel.getMovie().observe(getViewLifecycleOwner(), getMovie);

        showRecyclerCardView();
        showLoading(true);

        rvMovies.setHasFixedSize(true);
        setHasOptionsMenu(true);

    }

    private void showRecyclerCardView() {
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardViewMovieAdapter = new CardViewMovieAdapter();
        cardViewMovieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(cardViewMovieAdapter);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movie) {
            if (movie != null) {
                cardViewMovieAdapter.setData(movie);

                showLoading(false);
            }
        }
    };

    private Observer<ArrayList<Movie>> getSearchMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movie) {
            if (movie != null) {
                cardViewMovieAdapter.setData(movie);

                showLoading(false);
                if (movie.size() == 0)
                    Toast.makeText(getContext(), R.string.no_result, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.action_search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_movie));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    query = query.toLowerCase();
                    movieViewModel.setSearchMovie(query);
                    movieViewModel.getSearchMovie().observe(getViewLifecycleOwner(), getSearchMovies);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String nextText) {
                    return false;
                }
            });
        }

        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                movieViewModel.setMovie();
                return true;
            }
        });
    }
}
