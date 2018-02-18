package com.geekapps.andrii.gallerypicker.fragment.albumvideofragment;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.geekapps.andrii.gallerypicker.model.MediaModel;
import com.geekapps.andrii.gallerypicker.util.GroupHelper;
import com.geekapps.andrii.gallerypicker.util.VideoProvider;

import java.util.List;

public class AlbumVideoPresenter implements AlbumVideoContract.Presenter {

    private final AlbumVideoContract.View mView;
    private final VideoProvider mVideoProvider;

    AlbumVideoPresenter(AlbumVideoContract.View view, Context context) {
        mView = view;
        mVideoProvider = new VideoProvider(context, this);
    }

    @Override
    public void getVideos(LoaderManager loaderManager) {
        mVideoProvider.startLoadVideos(loaderManager);
    }

    @Override
    public void onLoadVideoSuccess(List<MediaModel> videoModels) {
        mView.onGetVideosSuccess(GroupHelper.getAlbums(videoModels));
    }

    @Override
    public void onLoadVideoFail() {
        mView.onGetVideosFail();
    }
}
