package com.geekapps.andrii.gallerypicker.model;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import com.geekapps.andrii.gallerypicker.util.BitmapDecoder;

import java.io.Serializable;

public class MediaModel implements Serializable {

    private static final int REQ_WIDTH = 180;
    private static final int REQ_HEIGHT = 180;

    private String mMediaName;
    private String mMediaPath;
    private String mMediaAlbumName;
    private String mPreviewThumb;

    private boolean mIsSelected;

    public MediaModel(String mediaName, String mediaPath, String mediaAlbumName, String previewThumb) {
        mMediaName = mediaName;
        mMediaPath = mediaPath;
        mMediaAlbumName = mediaAlbumName;
        mPreviewThumb = previewThumb;
    }

    public Bitmap getBitmap() {
        if (mPreviewThumb != null) {
            return ThumbnailUtils.createVideoThumbnail(mMediaPath, MediaStore.Images.Thumbnails.MINI_KIND);
        } else {
            return BitmapDecoder.decodeSmallBitmapFromFile(mMediaPath, REQ_WIDTH, REQ_HEIGHT);
        }
    }

    public MediaType getMediaType() {
        if (mPreviewThumb != null)
            return MediaType.VIDEO;
        else return MediaType.IMAGE;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }

    public String getMediaName() {
        return mMediaName;
    }

    public String getMediaPath() {
        return mMediaPath;
    }

    public String getMediaAlbumName() {
        return mMediaAlbumName;
    }
}
