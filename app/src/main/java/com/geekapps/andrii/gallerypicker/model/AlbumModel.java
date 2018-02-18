package com.geekapps.andrii.gallerypicker.model;

import java.io.Serializable;
import java.util.List;

public class AlbumModel implements Serializable {

    private String mAlbumName;
    private List<MediaModel> mMediaModels;

    public AlbumModel(String albumName, List<MediaModel> mediaModels) {
        mAlbumName = albumName;
        mMediaModels = mediaModels;
    }

    public AlbumModel() {
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public List<MediaModel> getMediaModels() {
        return mMediaModels;
    }
}
