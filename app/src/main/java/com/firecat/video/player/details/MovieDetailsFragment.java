package com.firecat.video.player.details;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firecat.video.player.Api;
import com.firecat.video.player.BaseApplication;
import com.firecat.video.player.Constants;
import com.firecat.video.player.Movie;
import com.firecat.video.player.R;
import com.firecat.video.player.Review;
import com.firecat.video.player.Video;
import com.firecat.video.player.databinding.FragmentMovieDetailsBinding;
import com.firecat.video.player.databinding.SubVideoBinding;
import com.firecat.video.player.view.HPosterView;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

import javax.inject.Inject;


public class MovieDetailsFragment extends Fragment implements MovieDetailsView, View.OnClickListener {
    @Inject
    MovieDetailsPresenter movieDetailsPresenter;

    HPosterView poster;
    CollapsingToolbarLayout collapsingToolbar;
    TextView title;
    TextView releaseDate;
    TextView rating;
    TextView overview;
    TextView label;
    LinearLayout trailers;
    HorizontalScrollView horizontalScrollView;
    @Nullable
    Toolbar toolbar;

    private Movie movie;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsFragment getInstance(@NonNull Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.MOVIE, movie);
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        movieDetailsFragment.setArguments(args);
        return movieDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseApplication) getActivity().getApplication()).createDetailsComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentMovieDetailsBinding binding = FragmentMovieDetailsBinding.inflate(inflater, container, false);


        poster = binding.moviePoster;
        collapsingToolbar = binding.collapsingToolbar;
        title = binding.movieName;
        releaseDate = binding.movieYear;
        rating = binding.movieRating;
        overview = binding.movieDescription;
        label = binding.trailersAndReviews.trailersLabel;
        trailers = binding.trailersAndReviews.trailers;
        horizontalScrollView = binding.trailersAndReviews.trailersContainer;
        toolbar = binding.toolbar;


        setToolbar();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            Movie movie = (Movie) getArguments().get(Constants.MOVIE);
            if (movie != null) {
                this.movie = movie;
                movieDetailsPresenter.setView(this);
                movieDetailsPresenter.showDetails((movie));
                movieDetailsPresenter.showFavoriteButton(movie);
            }
        }
    }

    private void setToolbar() {
        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        collapsingToolbar.setTitle(getString(R.string.movie_details));
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);

        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } else {
            // Don't inflate. Tablet is in landscape mode.
        }
    }

    @Override
    public void showDetails(Movie movie) {
        Glide.with(getContext()).load(Api.getBackdropPath(movie.getBackdropPath())).into(poster);
        title.setText(movie.getTitle());
        releaseDate.setText(String.format(getString(R.string.release_date), movie.getReleaseDate()));
        rating.setText(String.format(getString(R.string.rating), String.valueOf(movie.getVoteAverage())));
        overview.setText(movie.getOverview());
        movieDetailsPresenter.showTrailers(movie);
        movieDetailsPresenter.showReviews(movie);
    }

    @Override
    public void showTrailers(List<Video> trailers) {
        if (trailers.isEmpty()) {
            label.setVisibility(View.GONE);
            this.trailers.setVisibility(View.GONE);
            horizontalScrollView.setVisibility(View.GONE);

        } else {
            label.setVisibility(View.VISIBLE);
            this.trailers.setVisibility(View.VISIBLE);
            horizontalScrollView.setVisibility(View.VISIBLE);

            this.trailers.removeAllViews();
            LayoutInflater inflater = getActivity().getLayoutInflater();
            RequestOptions options = new RequestOptions()
                    .placeholder(R.color.colorPrimary)
                    .centerCrop()
                    .override(150, 150);

            for (Video trailer : trailers) {
                SubVideoBinding subVideoBinding = SubVideoBinding.inflate(inflater, this.trailers, false);
                ImageView thumbView = subVideoBinding.videoThumb;
                thumbView.setTag(R.id.glide_tag, Video.getUrl(trailer));
                thumbView.requestLayout();
                thumbView.setOnClickListener(this);
                Glide.with(requireContext())
                        .load(Video.getThumbnailUrl(trailer))
                        .apply(options)
                        .into(thumbView);
                this.trailers.addView(subVideoBinding.getRoot());
            }
        }
    }

    @Override
    public void showReviews(List<Review> reviews) {

    }

    @Override
    public void showFavorited() {
    }

    @Override
    public void showUnFavorited() {
    }

    public void onClick(View view) {
        final int id = view.getId();
        if(R.id.video_thumb == id) {
            onThumbnailClick(view);
        }else if(R.id.review_content == id) {
            onReviewClick((TextView) view);
        }
    }

    private void onReviewClick(TextView view) {
        if (view.getMaxLines() == 5) {
            view.setMaxLines(500);
        } else {
            view.setMaxLines(5);
        }
    }

    private void onThumbnailClick(View view) {
        String videoUrl = (String) view.getTag(R.id.glide_tag);
        Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(playVideoIntent);
    }

    private void onFavoriteClick() {
        movieDetailsPresenter.onFavoriteClick(movie);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        movieDetailsPresenter.destroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((BaseApplication) getActivity().getApplication()).releaseDetailsComponent();
    }
}
