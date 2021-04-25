package com.firecat.video.player.listing.sorting;

/**
 * @author arun
 */
class SortingDialogPresenterImpl implements SortingDialogPresenter {
    private SortingDialogView view;
    private SortingDialogInteractor sortingDialogInteractor;

    SortingDialogPresenterImpl(SortingDialogInteractor interactor) {
        sortingDialogInteractor = interactor;
    }

    @Override
    public void setView(SortingDialogView view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void setLastSavedOption() {
        if (isViewAttached()) {
            int selectedOption = sortingDialogInteractor.getSelectedSortingOption();

            if (selectedOption == SortType.MOST_POPULAR.getValue()) {
                view.setPopularChecked();
            } else {
                view.setFavoritesChecked();
            }
        }
    }

    private boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void onPopularMoviesSelected() {
        if (isViewAttached()) {
            sortingDialogInteractor.setSortingOption(SortType.MOST_POPULAR);
            view.dismissDialog();
        }
    }

    @Override
    public void onHighestRatedMoviesSelected() {
        if (isViewAttached()) {
            view.dismissDialog();
        }
    }

    @Override
    public void onNewestMoviesSelected() {
        if (isViewAttached()) {
            view.dismissDialog();
        }
    }

    @Override
    public void onFavoritesSelected() {
        if (isViewAttached()) {
            sortingDialogInteractor.setSortingOption(SortType.FAVORITES);
            view.dismissDialog();
        }
    }
}
