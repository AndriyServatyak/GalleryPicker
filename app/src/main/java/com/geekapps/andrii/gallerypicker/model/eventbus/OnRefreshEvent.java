package com.geekapps.andrii.gallerypicker.model.eventbus;

import com.geekapps.andrii.gallerypicker.model.MediaModel;

import java.util.List;

public class OnRefreshEvent {

    private List<MediaModel> mSelectedItems;

    public OnRefreshEvent(List<MediaModel> selectedItems) {
        mSelectedItems = selectedItems;
    }

    public List<MediaModel> getSelectedItems() {
        return mSelectedItems;
    }
}
