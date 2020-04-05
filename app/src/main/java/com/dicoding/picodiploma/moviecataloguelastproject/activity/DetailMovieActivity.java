package com.dicoding.picodiploma.moviecataloguelastproject.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.moviecataloguelastproject.R;
import com.dicoding.picodiploma.moviecataloguelastproject.adapter.FavoriteMovieAdapter;
import com.dicoding.picodiploma.moviecataloguelastproject.database.MovieHelper;
import com.dicoding.picodiploma.moviecataloguelastproject.mappinghelper.MappingMovieHelper;
import com.dicoding.picodiploma.moviecataloguelastproject.model.Movie;

import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.CONTENT_URI;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.MOVIE_ID;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.OVERVIEW;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.PHOTO;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.RELEASE_DATE;
import static com.dicoding.picodiploma.moviecataloguelastproject.database.DatabaseContract.FavoriteMovie.TITLE;


public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdd;
    private ImageButton btnDelete;

    private FavoriteMovieAdapter favoriteMovieAdapter;

    private Movie movie;

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

        btnAdd = findViewById(R.id.btn_add_favorite);
        btnAdd.setOnClickListener(this);
        btnDelete = findViewById(R.id.btn_delete_favorite);
        btnDelete.setOnClickListener(this);

        ProgressBar progressBar = findViewById(R.id.detail_movie_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        favoriteMovieAdapter = new FavoriteMovieAdapter(this);
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        MovieHelper movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();
        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + movie.getId()),
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
        String idMovie = Integer.toString(movie.getId());
        if (movieHelper.checkFavoriteMovie(idMovie)) {
            btnAdd.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
        }

        String url_image = "http://image.tmdb.org/t/p/w500" + movie.getPhoto();

        if (movie != null) {
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_favorite) {
            movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            favoriteMovieAdapter.addItem(movie);

            ContentValues values = new ContentValues();
            values.put(MOVIE_ID, movie.getId());
            values.put(TITLE, movie.getTitle());
            values.put(RELEASE_DATE, movie.getRelease_date());
            values.put(OVERVIEW, movie.getOverview());
            values.put(PHOTO, movie.getPhoto());

            String toastAddFav = getString(R.string.item_save);

            getContentResolver().insert(CONTENT_URI, values);

            btnAdd.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
            Toast.makeText(DetailMovieActivity.this, toastAddFav, Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn_delete_favorite) {
            movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            favoriteMovieAdapter.removeItem(movie);

            String toastDelete = getString(R.string.item_delete);
            getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + movie.getId()), null, null);
            Toast.makeText(DetailMovieActivity.this, toastDelete, Toast.LENGTH_SHORT).show();
            btnAdd.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        }
    }
}