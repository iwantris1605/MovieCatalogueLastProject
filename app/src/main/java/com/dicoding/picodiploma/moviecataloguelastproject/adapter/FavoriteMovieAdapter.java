package com.dicoding.picodiploma.moviecataloguelastproject.adapter;

import android.app.Activity;
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
import com.dicoding.picodiploma.moviecataloguelastproject.utils.CustomOnItemClickListener;
import com.dicoding.picodiploma.moviecataloguelastproject.R;
import com.dicoding.picodiploma.moviecataloguelastproject.activity.DetailMovieActivity;
import com.dicoding.picodiploma.moviecataloguelastproject.model.Movie;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteViewHolder> {

    private ArrayList<Movie> listMovies = new ArrayList<>();
    private Activity activity;

    public FavoriteMovieAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Movie> getListMovies() {
        return listMovies;
    }

    public void setListMovies(ArrayList<Movie> listMovies) {
        if (listMovies.size() > 0) {
            this.listMovies.clear();
        }
        this.listMovies.addAll(listMovies);

        notifyDataSetChanged();
    }

    public void addItem(Movie movie) {
        this.listMovies.add(movie);
        notifyItemInserted(listMovies.size() - 1);
    }


    public void removeItem(Movie movie) {
        this.listMovies.remove(movie);
        notifyItemRemoved(listMovies.size());
    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_list_movie_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteMovieAdapter.FavoriteViewHolder holder, int position) {
        final Movie movie = listMovies.get(position);
        String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();

        Glide.with(holder.itemView.getContext())
                .load(url_image)
                .into(holder.imgPhoto);
        holder.tvName.setText(movie.getTitle());
        holder.tvRelease.setText(movie.getRelease_date());
        holder.tvDescription.setText(movie.getOverview());
        holder.cvFavMovie.setOnClickListener(new CustomOnItemClickListener(position, (view, position1) -> {
            Intent intent = new Intent(activity, DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, position1);
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, listMovies.get(position1));
            holder.itemView.getContext().startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvRelease, tvDescription;
        CardView cvFavMovie;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.iv_fav_movie_photo);
            tvName = itemView.findViewById(R.id.tv_fav_movie_name);
            tvRelease = itemView.findViewById(R.id.tv_fav_movie_release);
            tvDescription = itemView.findViewById(R.id.tv_fav_movie_description);
            cvFavMovie = itemView.findViewById(R.id.cv_movie_favorite);
        }
    }
}
