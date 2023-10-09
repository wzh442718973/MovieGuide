package com.firecat.video.player.listing.sorting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.firecat.video.player.BaseApplication;
import com.firecat.video.player.R;
import com.firecat.video.player.databinding.SortingOptionsBinding;
import com.firecat.video.player.listing.MoviesListingPresenter;

import javax.inject.Inject;


/**
 * @author arun
 */
public class SortingDialogFragment extends DialogFragment implements SortingDialogView, RadioGroup.OnCheckedChangeListener {
    @Inject
    SortingDialogPresenter sortingDialogPresenter;

    RadioButton mostPopular;
    RadioGroup sortingOptionsGroup;

    private static MoviesListingPresenter moviesListingPresenter;

    public static SortingDialogFragment newInstance(MoviesListingPresenter moviesListingPresenter) {
        SortingDialogFragment.moviesListingPresenter = moviesListingPresenter;
        return new SortingDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseApplication) getActivity().getApplication()).getListingComponent().inject(this);
        sortingDialogPresenter.setView(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        SortingOptionsBinding binding = SortingOptionsBinding.inflate(inflater);

        mostPopular = binding.mostPopular;
        sortingOptionsGroup = binding.sortingGroup;

        initViews();

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(binding.getRoot());
        dialog.setTitle(R.string.sort_by);
        dialog.show();
        return dialog;
    }

    private void initViews() {
        sortingDialogPresenter.setLastSavedOption();
        sortingOptionsGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void setPopularChecked() {
        mostPopular.setChecked(true);
    }


    @Override
    public void setNewestChecked() {
    }

    @Override
    public void setHighestRatedChecked() {
    }

    @Override
    public void setFavoritesChecked() {
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if(R.id.most_popular == checkedId) {
                sortingDialogPresenter.onPopularMoviesSelected();
                moviesListingPresenter.firstPage();
        }
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sortingDialogPresenter.destroy();
    }
}
