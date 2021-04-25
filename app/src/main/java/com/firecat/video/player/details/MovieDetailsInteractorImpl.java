package com.firecat.video.player.details;

import com.firecat.video.player.Review;
import com.firecat.video.player.ReviewsWrapper;
import com.firecat.video.player.Video;
import com.firecat.video.player.VideoWrapper;
import com.firecat.video.player.network.TmdbWebService;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author arun
 */
class MovieDetailsInteractorImpl implements MovieDetailsInteractor {

    private TmdbWebService tmdbWebService;

    MovieDetailsInteractorImpl(TmdbWebService tmdbWebService) {
        this.tmdbWebService = tmdbWebService;
    }

    @Override
    public Observable<List<Video>> getTrailers(final String id) {
        return tmdbWebService.trailers(id).map(VideoWrapper::getVideos);
    }

    @Override
    public Observable<List<Review>> getReviews(final String id) {
        return tmdbWebService.reviews(id).map(ReviewsWrapper::getReviews);
    }

}
