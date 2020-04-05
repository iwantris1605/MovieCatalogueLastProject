package com.dicoding.picodiploma.favoriteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.dicoding.picodiploma.favoriteapp.adapter.ViewPagerAdapter;
import com.dicoding.picodiploma.favoriteapp.fragment.MovieFavoriteFragment;
import com.dicoding.picodiploma.favoriteapp.fragment.TvShowFavoriteFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        adapter.addFragment(new MovieFavoriteFragment(), getResources().getString(R.string.tab_text_1));
        adapter.addFragment(new TvShowFavoriteFragment(), getResources().getString(R.string.tab_text_2));
        viewPager.setAdapter(adapter);

    }
}
