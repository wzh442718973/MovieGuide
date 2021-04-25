package com.firecat.video.player.listing.sorting;

/**
 * @author arun
 */
interface SortingDialogView {
    void setPopularChecked();

    void setNewestChecked();

    void setHighestRatedChecked();

    void setFavoritesChecked();

    void dismissDialog();

}
