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
import com.dicoding.picodiploma.moviecataloguelastproject.adapter.CardViewTvShowAdapter;
import com.dicoding.picodiploma.moviecataloguelastproject.model.TvShow;
import com.dicoding.picodiploma.moviecataloguelastproject.viewModel.TvShowViewModel;

import java.util.ArrayList;
import java.util.Objects;


public class TvShowFragment extends Fragment {
    private TvShowViewModel tvShowViewModel;
    private CardViewTvShowAdapter cardViewTvShowAdapter;
    private RecyclerView rvTvShows;
    private ProgressBar progressBar;


    public TvShowFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTvShows = view.findViewById(R.id.rv_tvshow);
        progressBar = view.findViewById(R.id.tv_show_progressBar);

        tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel.class);
        tvShowViewModel.setTvShow();
        tvShowViewModel.getTvShow().observe(getViewLifecycleOwner(), getTvShow);

        showRecyclerCardView();
        showLoading(true);

        rvTvShows.setHasFixedSize(true);
        setHasOptionsMenu(true);

    }
    private void showRecyclerCardView() {
        rvTvShows.setLayoutManager(new LinearLayoutManager(getContext()));
        cardViewTvShowAdapter = new CardViewTvShowAdapter();
        cardViewTvShowAdapter.notifyDataSetChanged();
        rvTvShows.setAdapter(cardViewTvShowAdapter);
    }
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShow) {
            if (tvShow != null) {
                cardViewTvShowAdapter.setDataTvShow(tvShow);
                showLoading(false);
            }
        }
    };

    private Observer<ArrayList<TvShow>> getSearchTvShows = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShow) {
            if (tvShow != null) {
                cardViewTvShowAdapter.setDataTvShow(tvShow);
                showLoading(false);
                if (tvShow.size() == 0)
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
            searchView.setQueryHint(getResources().getString(R.string.search_tv));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    query = query.toLowerCase();
                    tvShowViewModel.setSearchTvShow(query);
                    tvShowViewModel.getSearchTvShow().observe(getViewLifecycleOwner(), getSearchTvShows);
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
                tvShowViewModel.setTvShow();
                return true;
            }
        });
    }
}
