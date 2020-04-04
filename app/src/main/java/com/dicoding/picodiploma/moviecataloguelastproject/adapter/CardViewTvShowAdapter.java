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
import com.dicoding.picodiploma.moviecataloguelastproject.activity.TvShowDetailActivity;
import com.dicoding.picodiploma.moviecataloguelastproject.model.TvShow;

import java.util.ArrayList;

public class CardViewTvShowAdapter extends RecyclerView.Adapter<CardViewTvShowAdapter.CardViewViewHolder> {

    private ArrayList<TvShow> tvData = new ArrayList<>();

    public void setDataTvShow(ArrayList<TvShow> itemTv) {
        tvData.clear();
        tvData.addAll(itemTv);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_list_tv_show, viewGroup, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, final int position) {
        TvShow tvShow = tvData.get(position);
        String url_image = "http://image.tmdb.org/t/p/w185" + tvShow.getPoster_path();

        Glide.with(holder.itemView.getContext())
                .load(url_image)
                .into(holder.imgPhoto);
        holder.tvName.setText(tvShow.getName());
        holder.tvRelease.setText(tvShow.getFirst_air_date());
        holder.tvDescription.setText(tvShow.getOverview());
        holder.cvTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tvShowIntent = new Intent(holder.itemView.getContext(), TvShowDetailActivity.class);
                tvShowIntent.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, tvData.get(position));
                holder.itemView.getContext().startActivity(tvShowIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    static class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvRelease, tvDescription;
        CardView cvTvShow;

        CardViewViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.iv_tv_show_photo);
            tvName = itemView.findViewById(R.id.tv_tv_show_name);
            tvRelease = itemView.findViewById(R.id.tv_tv_show_release);
            tvDescription = itemView.findViewById(R.id.tv_tv_show_description);
            cvTvShow = itemView.findViewById(R.id.cv_tv_show);
        }
    }
}
