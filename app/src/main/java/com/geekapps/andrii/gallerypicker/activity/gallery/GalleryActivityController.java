package com.geekapps.andrii.gallerypicker.activity.gallery;

import com.geekapps.andrii.gallerypicker.model.AlbumModel;
import com.geekapps.andrii.gallerypicker.model.MediaModel;

import java.util.List;

public interface GalleryActivityController {
    void goToMediaFragment(AlbumModel mediaModels);

    void goToPhotoFullScreen(List<MediaModel> mediaModels, int selectedPosition);

    void goToVideoPlayer(MediaModel mediaModel);

    void setResult(List<MediaModel> selectedValues);

    void goBack();

}
