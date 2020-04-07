package com.dicoding.picodiploma.favoriteapp.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.favoriteapp.R;
import com.dicoding.picodiploma.favoriteapp.mappinghelper.MappingMovieHelper;
import com.dicoding.picodiploma.favoriteapp.model.Movie;

import java.util.Objects;

import static com.dicoding.picodiploma.favoriteapp.database.DatabaseContract.FavoriteMovie.CONTENT_URI;


public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    public DetailMovieActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.detail_movie);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        ProgressBar progressBar = findViewById(R.id.detail_movie_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + Objects.requireNonNull(movie).getId()),
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                movie = MappingMovieHelper.mapCursorToObject(cursor);
                cursor.close();
            }
        }

        String url_image = "https://image.tmdb.org/t/p/w500" + movie.getPhoto();

        ImageView imgPhoto = findViewById(R.id.movieImage);
        Glide.with(DetailMovieActivity.this)
                .load(url_image)
                .into(imgPhoto);
        progressBar.setVisibility(View.GONE);

        TextView txtName = findViewById(R.id.movieName);
        txtName.setText(movie.getTitle());

        TextView txtRelease = findViewById(R.id.movieRelease);
        txtRelease.setText(movie.getRelease_date());

        TextView txtDescription = findViewById(R.id.movieDescription);
        txtDescription.setText(movie.getOverview());
    }
}