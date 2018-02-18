package com.geekapps.andrii.gallerypicker.model.eventbus;

import com.geekapps.andrii.gallerypicker.model.MediaModel;

import java.util.List;

public class OnShowImageEvent {

    private List<MediaModel> mMediaModels;
    private int mSelectedPosition;

    public OnShowImageEvent(List<MediaModel> mediaModel, int selectedPosition) {
        mMediaModels = mediaModel;
        mSelectedPosition = selectedPosition;
    }

    public List<MediaModel> getMediaModels() {
        return mMediaModels;
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }
}
