package com.dicoding.picodiploma.favoriteapp.activity;

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
import com.dicoding.picodiploma.favoriteapp.R;
import com.dicoding.picodiploma.favoriteapp.adapter.FavoriteTvShowAdapter;
import com.dicoding.picodiploma.favoriteapp.mappinghelper.MappingTvShowHelper;
import com.dicoding.picodiploma.favoriteapp.model.TvShow;

import static com.dicoding.picodiploma.favoriteapp.database.DatabaseContract.FavoriteTvShow.CONTENT_URI_TV;
import static com.dicoding.picodiploma.favoriteapp.database.DatabaseContract.FavoriteTvShow.FIRST_AIR_DATE;
import static com.dicoding.picodiploma.favoriteapp.database.DatabaseContract.FavoriteTvShow.NAME;
import static com.dicoding.picodiploma.favoriteapp.database.DatabaseContract.FavoriteTvShow.OVERVIEW;
import static com.dicoding.picodiploma.favoriteapp.database.DatabaseContract.FavoriteTvShow.POSTER_PATH;
import static com.dicoding.picodiploma.favoriteapp.database.DatabaseContract.FavoriteTvShow.TV_SHOW_ID;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdd;
    private ImageButton btnDelete;

    private FavoriteTvShowAdapter favoriteTvShowAdapter;

    private TvShow tvShow;

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

        btnAdd = findViewById(R.id.btn_add_favorite);
        btnAdd.setOnClickListener(this);
        btnDelete = findViewById(R.id.btn_delete_favorite);
        btnDelete.setOnClickListener(this);

        ProgressBar progressBar = findViewById(R.id.detail_tv_show_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        favoriteTvShowAdapter = new FavoriteTvShowAdapter(this);
        tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI_TV + "/" + tvShow.getId()),
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

        String url_image = "http://image.tmdb.org/t/p/w500" + tvShow.getPoster_path();

        if (tvShow != null) {
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_favorite) {
            tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
            favoriteTvShowAdapter.addItem(tvShow);

            ContentValues values = new ContentValues();
            values.put(TV_SHOW_ID, tvShow.getId());
            values.put(NAME, tvShow.getName());
            values.put(FIRST_AIR_DATE, tvShow.getFirst_air_date());
            values.put(OVERVIEW, tvShow.getOverview());
            values.put(POSTER_PATH, tvShow.getPoster_path());

            String toastAddFavTv = getString(R.string.item_save);

            getContentResolver().insert(CONTENT_URI_TV, values);

            btnAdd.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
            Toast.makeText(TvShowDetailActivity.this, toastAddFavTv, Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn_delete_favorite) {
            tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
            favoriteTvShowAdapter.removeItem(tvShow);
            String toastDelete = getString(R.string.item_delete);
            getContentResolver().delete(Uri.parse(CONTENT_URI_TV + "/" + tvShow.getId()), null, null);
            Toast.makeText(TvShowDetailActivity.this, toastDelete, Toast.LENGTH_SHORT).show();
            btnAdd.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        }
    }
}
