package com.dicoding.picodiploma.moviecataloguelastproject.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.moviecataloguelastproject.R;
import com.dicoding.picodiploma.moviecataloguelastproject.activity.DetailMovieActivity;
import com.dicoding.picodiploma.moviecataloguelastproject.model.Movie;

import java.util.ArrayList;

public class CardViewMovieAdapter extends RecyclerView.Adapter<CardViewMovieAdapter.CardViewViewHolder> {

    private ArrayList<Movie> mData = new ArrayList<>();

    public void setData(ArrayList<Movie> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_list_movie, viewGroup, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, final int position) {
        Movie movie = mData.get(position);
        String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();

        Glide.with(holder.itemView.getContext())
                .load(url_image)
                .into(holder.imgPhoto);
        holder.tvName.setText(movie.getTitle());
        holder.tvRelease.setText(movie.getRelease_date());
        holder.tvDescription.setText(movie.getOverview());
        holder.cvMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieIntent = new Intent(holder.itemView.getContext(), DetailMovieActivity.class);
                movieIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, mData.get(position));
                holder.itemView.getContext().startActivity(movieIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvRelease, tvDescription;
        CardView cvMovie;

        CardViewViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.iv_movie_photo);
            tvName = itemView.findViewById(R.id.tv_movie_name);
            tvRelease = itemView.findViewById(R.id.tv_movie_release);
            tvDescription = itemView.findViewById(R.id.tv_movie_description);
            cvMovie = itemView.findViewById(R.id.cv_movie);
        }
    }
}
