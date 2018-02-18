package com.geekapps.andrii.gallerypicker.model.eventbus;

import com.geekapps.andrii.gallerypicker.model.MediaModel;


public class OnShowVideoEvent {

    private MediaModel mSelectedItem;

    public OnShowVideoEvent(MediaModel selectedItem) {
        mSelectedItem = selectedItem;
    }

    public MediaModel getSelectedItem() {
        return mSelectedItem;
    }
}
