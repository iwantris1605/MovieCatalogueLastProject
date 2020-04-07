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
import com.dicoding.picodiploma.favoriteapp.mappinghelper.MappingTvShowHelper;
import com.dicoding.picodiploma.favoriteapp.model.TvShow;

import java.util.Objects;

import static com.dicoding.picodiploma.favoriteapp.database.DatabaseContract.FavoriteTvShow.CONTENT_URI_TV;

public class TvShowDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TVSHOW = "extra_tvshow";

    public TvShowDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.tvshow_detail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ProgressBar progressBar = findViewById(R.id.detail_tv_show_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI_TV + "/" + Objects.requireNonNull(tvShow).getId()),
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                tvShow = MappingTvShowHelper.mapCursorToObject(cursor);
                cursor.close();
            }
        }
        String url_image = "https://image.tmdb.org/t/p/w500" + tvShow.getPoster_path();

        ImageView imgPhoto = findViewById(R.id.tvShowImage);
        Glide.with(TvShowDetailActivity.this)
                .load(url_image)
                .into(imgPhoto);
        progressBar.setVisibility(View.GONE);

        TextView txtName = findViewById(R.id.tvShowName);
        txtName.setText(tvShow.getName());

        TextView txtRelease = findViewById(R.id.tvShowRelease);
        txtRelease.setText(tvShow.getFirst_air_date());

        TextView txtDescription = findViewById(R.id.tvShowDescription);
        txtDescription.setText(tvShow.getOverview());

    }
}
