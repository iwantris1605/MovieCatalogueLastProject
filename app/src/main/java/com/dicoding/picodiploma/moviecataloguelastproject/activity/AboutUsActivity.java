package com.dicoding.picodiploma.moviecataloguelastproject.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.picodiploma.moviecataloguelastproject.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.about_us);
        }
    }
}
