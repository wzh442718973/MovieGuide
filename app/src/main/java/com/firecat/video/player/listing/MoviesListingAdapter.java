package com.firecat.video.player.listing;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firecat.video.player.Api;
import com.firecat.video.player.Movie;
import com.firecat.video.player.R;
import com.firecat.video.player.databinding.MovieGridItemBinding;

import java.util.List;


/**
 * @author arun
 */
public class MoviesListingAdapter extends RecyclerView.Adapter<MoviesListingAdapter.ViewHolder> {
    private List<Movie> movies;
    private Context context;
    private MoviesListingView view;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster;
        View titleBackground;
        TextView name;

        public Movie movie;

        public ViewHolder(MovieGridItemBinding binding) {
            super(binding.getRoot());

            poster = binding.moviePoster;
            titleBackground = binding.titleBackground;
            name = binding.movieName;
        }

        @Override
        public void onClick(View view) {
            MoviesListingAdapter.this.view.onMovieClicked(movie);
        }
    }

    public MoviesListingAdapter(List<Movie> movies, MoviesListingView moviesView) {
        this.movies = movies;
        view = moviesView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(MovieGridItemBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(holder);
        holder.movie = movies.get(position);
        holder.name.setText(holder.movie.getTitle());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.HIGH);

        Glide.with(context)
                .asBitmap()
                .load(Api.getPosterPath(holder.movie.getPosterPath()))
                .apply(options)
                .into(new BitmapImageViewTarget(holder.poster) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(bitmap, transition);
                        Palette.from(bitmap).generate(palette -> setBackgroundColor(palette, holder));
                    }
                });
    }

    private void setBackgroundColor(Palette palette, ViewHolder holder) {
        holder.titleBackground.setBackgroundColor(palette.getVibrantColor(context
                .getResources().getColor(R.color.black_translucent_60)));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
