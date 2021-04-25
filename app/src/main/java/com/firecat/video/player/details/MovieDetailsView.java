package com.firecat.video.player.details;

import com.firecat.video.player.Movie;
import com.firecat.video.player.Review;
import com.firecat.video.player.Video;

import java.util.List;

/**
 * @author arun
 */
interface MovieDetailsView {
    void showDetails(Movie movie);

    void showTrailers(List<Video> trailers);

    void showReviews(List<Review> reviews);

    void showFavorited();

    void showUnFavorited();
}
