package com.geekapps.andrii.gallerypicker.model.eventbus;

import java.io.File;

public class OnFolderClickedEvent {

    private File mFile;

    public OnFolderClickedEvent(File file) {
        mFile = file;
    }

    public File getFile() {
        return mFile;
    }
}
