package com.dicoding.picodiploma.moviecataloguelastproject;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dicoding.picodiploma.moviecataloguelastproject.activity.AboutUsActivity;
import com.dicoding.picodiploma.moviecataloguelastproject.activity.SettingActivity;
import com.dicoding.picodiploma.moviecataloguelastproject.fragment.FavoriteFragment;
import com.dicoding.picodiploma.moviecataloguelastproject.fragment.MovieFragment;
import com.dicoding.picodiploma.moviecataloguelastproject.fragment.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_movie);
        }
        if (savedInstanceState == null) {
            loadFragment(new MovieFragment());
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                Fragment fragment;

                switch (item.getItemId()) {
                    case R.id.navigation_movie:
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(R.string.title_movie);
                        }
                        fragment = new MovieFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_tv_show:
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(R.string.title_tv_show);
                        }
                        fragment = new TvShowFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_favorite:
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(R.string.favorite);
                        }
                        fragment = new FavoriteFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }
        static {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.action_setting:
                Intent notifIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(notifIntent);
                break;
            case R.id.action_change_language:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.action_about_us:
                Intent usIntent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(usIntent);
                break;
            case R.id.action_exit:
                moveTaskToBack(true);
                break;
        }
    }
}
