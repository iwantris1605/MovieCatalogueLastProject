package com.dicoding.picodiploma.moviecataloguelastproject.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.moviecataloguelastproject.R;
import com.dicoding.picodiploma.moviecataloguelastproject.database.MovieHelper;
import com.dicoding.picodiploma.moviecataloguelastproject.model.Movie;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private final Context mContext;
    private final MovieHelper movieHelper;
    private ArrayList<Movie> listMovies = new ArrayList<>();

    StackRemoteViewsFactory(Context context) {
        mContext = context;
        movieHelper = MovieHelper.getInstance(mContext);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        listMovies = movieHelper.getFavoriteMovie();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listMovies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500/" + listMovies.get(position).getPhoto())
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
        rv.setImageViewBitmap(R.id.imageView, bitmap);
            Log.d("Widget", "Success");
        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "Error");
        }
        Bundle extras = new Bundle();
        extras.putInt(MovieFavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
