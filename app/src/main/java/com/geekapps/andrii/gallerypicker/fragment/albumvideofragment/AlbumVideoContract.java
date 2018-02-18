package com.geekapps.andrii.gallerypicker.fragment.albumvideofragment;


import android.support.v4.app.LoaderManager;

import com.geekapps.andrii.gallerypicker.model.AlbumModel;
import com.geekapps.andrii.gallerypicker.util.VideoProvider;

import java.util.List;

public interface AlbumVideoContract {

    interface View {
        void onGetVideosSuccess(List<AlbumModel> albumVideoModels);

        void onGetVideosFail();
    }

    interface Presenter extends VideoProvider.OnLoadVideoCallback {
        void getVideos(LoaderManager loaderManager);
    }
}
