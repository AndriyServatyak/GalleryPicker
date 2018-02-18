package com.geekapps.andrii.gallerypicker.fragment.albumimagefragment;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.geekapps.andrii.gallerypicker.model.MediaModel;
import com.geekapps.andrii.gallerypicker.util.GroupHelper;
import com.geekapps.andrii.gallerypicker.util.ImageProvider;

import java.util.List;

public class AlbumImageFragmentPresenter implements AlbumImageFragmentContract.Presenter {

    private final AlbumImageFragmentContract.View mView;
    private ImageProvider mImageProvider;

    AlbumImageFragmentPresenter(AlbumImageFragmentContract.View view, Context context) {
        mView = view;
        mImageProvider = new ImageProvider(context, this);
    }

    @Override
    public void getGalleryImageAlbums(LoaderManager loaderManager) {
        mImageProvider.startLoadImages(loaderManager);
    }

    @Override
    public void onLoadImagesSuccess(List<MediaModel> photoModels) {
        mView.onGetImageAlbumsSuccess(GroupHelper.getAlbums(photoModels));
    }

    @Override
    public void onLoadImagesFail() {
        mView.onGetImageAlbumsFail();
    }
}
