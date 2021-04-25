package com.firecat.video.player;

import com.firecat.video.player.details.DetailsComponent;
import com.firecat.video.player.details.DetailsModule;
import com.firecat.video.player.favorites.FavoritesModule;
import com.firecat.video.player.listing.ListingComponent;
import com.firecat.video.player.listing.ListingModule;
import com.firecat.video.player.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author arunsasidharan
 * @author pulkitkumar
 */
@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        FavoritesModule.class})
public interface AppComponent {
    DetailsComponent plus(DetailsModule detailsModule);

    ListingComponent plus(ListingModule listingModule);
}
