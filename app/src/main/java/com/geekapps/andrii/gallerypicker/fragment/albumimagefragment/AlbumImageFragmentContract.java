package com.geekapps.andrii.gallerypicker.fragment.albumimagefragment;

import android.support.v4.app.LoaderManager;

import com.geekapps.andrii.gallerypicker.model.AlbumModel;
import com.geekapps.andrii.gallerypicker.util.ImageProvider;

import java.util.List;

public interface AlbumImageFragmentContract {

    interface View {
        void onGetImageAlbumsSuccess(List<AlbumModel> albums);

        void onGetImageAlbumsFail();
    }

    interface Presenter extends ImageProvider.OnLoadImagesCallback {
        void getGalleryImageAlbums(LoaderManager loaderManager);
    }
}
