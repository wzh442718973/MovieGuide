package com.firecat.video.player.listing;

/**
 * @author arun
 */
public interface MoviesListingPresenter {
    void firstPage();

    void nextPage();

    void setView(MoviesListingView view);

    void searchMovie(String searchText);

    void searchMovieBackPressed();

    void destroy();
}
