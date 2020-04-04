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
import com.dicoding.picodiploma.moviecataloguelastproject.activity.TvShowDetailActivity;
import com.dicoding.picodiploma.moviecataloguelastproject.model.TvShow;

import java.util.ArrayList;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteViewHolder> {

    private ArrayList<TvShow> listTvShows = new ArrayList<>();
    private Activity activityTv;

    public FavoriteTvShowAdapter(Activity activityTv) {
        this.activityTv = activityTv;
    }

    public ArrayList<TvShow> getListTvShows() {
        return listTvShows;
    }

    public void setListTvShows(ArrayList<TvShow> listTvShows) {
        if (listTvShows.size() > 0) {
            this.listTvShows.clear();
        }
        this.listTvShows.addAll(listTvShows);

        notifyDataSetChanged();
    }

    public void addItem(TvShow tvShow) {
        this.listTvShows.add(tvShow);
        notifyItemInserted(listTvShows.size() - 1);
    }


    public void removeItem(TvShow tvShow) {
        this.listTvShows.remove(tvShow);
        notifyItemRemoved(listTvShows.size());
    }

    @NonNull
    @Override
    public FavoriteTvShowAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_list_tv_favorite, parent, false);
        return new FavoriteTvShowAdapter.FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteTvShowAdapter.FavoriteViewHolder holder, int position) {
        final TvShow tvShow = listTvShows.get(position);
        String url_image = "http://image.tmdb.org/t/p/w185" + tvShow.getPoster_path();

        Glide.with(holder.itemView.getContext())
                .load(url_image)
                .into(holder.imgPhoto);
        holder.tvName.setText(tvShow.getName());
        holder.tvRelease.setText(tvShow.getFirst_air_date());
        holder.tvDescription.setText(tvShow.getOverview());
        holder.cvFavTvShow.setOnClickListener(new CustomOnItemClickListener(position, (view, position1) -> {
            Intent intent = new Intent(activityTv, TvShowDetailActivity.class);
            intent.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, position1);
            intent.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, listTvShows.get(position1));
            holder.itemView.getContext().startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return listTvShows.size();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvRelease, tvDescription;
        CardView cvFavTvShow;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.iv_fav_tv_photo);
            tvName = itemView.findViewById(R.id.tv_fav_tv_name);
            tvRelease = itemView.findViewById(R.id.tv_fav_tv_release);
            tvDescription = itemView.findViewById(R.id.tv_fav_tv_description);
            cvFavTvShow = itemView.findViewById(R.id.cv_tv_favorite);
        }
    }
}
