package com.example.movieradar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movies;
    private OnItemClickListener listener;

    public MovieAdapter(Context context, List<Movie> movies, OnItemClickListener listener) {
        this.context = context;
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        String baseUrl = "https://image.tmdb.org/t/p/original";
        Glide.with(context)
                .load(baseUrl + movie.posterPath)
                .centerCrop()
                .into(holder.posterImageView);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
        }
    }
}
