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
import com.dicoding.picodiploma.favoriteapp.adapter.FavoriteTvShowAdapter;
import com.dicoding.picodiploma.favoriteapp.database.DatabaseContract;
import com.dicoding.picodiploma.favoriteapp.mappinghelper.MappingTvShowHelper;
import com.dicoding.picodiploma.favoriteapp.model.TvShow;
import com.dicoding.picodiploma.favoriteapp.utils.LoadTvShowCallback;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;


public class TvShowFavoriteFragment extends Fragment implements LoadTvShowCallback {

    private ProgressBar progressBar;
    private RecyclerView rvFavTv;
    private FavoriteTvShowAdapter favoriteTvShowAdapter;

    private static final String EXTRA_STATE2 = "EXTRA_STATE2";


    public TvShowFavoriteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tv_showfavorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.tv_favorite_progressbar);
        rvFavTv = view.findViewById(R.id.rv_tv_favorite);
        rvFavTv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvFavTv.setHasFixedSize(true);
        favoriteTvShowAdapter = new FavoriteTvShowAdapter(getActivity());
        rvFavTv.setAdapter(favoriteTvShowAdapter);

        if (savedInstanceState == null) {
            new TvShowFavoriteFragment.LoadTvShowAsync(getContext(), this).execute();
        } else {
            ArrayList<TvShow> arrayList = savedInstanceState.getParcelableArrayList(EXTRA_STATE2);
            if (arrayList != null) {
                favoriteTvShowAdapter.setListTvShows(arrayList);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE2, favoriteTvShowAdapter.getListTvShows());
    }

    @Override
    public void preExecute() {
        new Thread(() -> progressBar.setVisibility(View.VISIBLE));
    }

    @Override
    public void postExecute(ArrayList<TvShow> tvShow) {
        progressBar.setVisibility(View.INVISIBLE);
        if (tvShow.size() > 0) {
            favoriteTvShowAdapter.setListTvShows(tvShow);
        } else {
            favoriteTvShowAdapter.setListTvShows(new ArrayList<>());
            showSnackbarMessage(getString(R.string.empty_tvshow_favorite));
        }
    }

    private static class LoadTvShowAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadTvShowCallback> weakCallback;

        private LoadTvShowAsync(Context context, LoadTvShowCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor tvCursor = context.getContentResolver().query(DatabaseContract.FavoriteTvShow.CONTENT_URI_TV, null, null, null, null);
            return MappingTvShowHelper.mapCursorToArrayList(Objects.requireNonNull(tvCursor));
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> tvShow) {
            super.onPostExecute(tvShow);
            weakCallback.get().postExecute(tvShow);

        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFavTv, message, Snackbar.LENGTH_SHORT).show();
    }
}